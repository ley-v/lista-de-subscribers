package com.example.a02crudcomroom.ui.subscriberlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a02crudcomroom.R
import com.example.a02crudcomroom.data.db.AppDatabase
import com.example.a02crudcomroom.repository.DatabaseDataSource
import com.example.a02crudcomroom.repository.SubscriberRepository
import kotlinx.android.synthetic.main.subscriber_list_fragment.*

class SubscriberListFragment : Fragment(R.layout.subscriber_list_fragment) {

    private val viewModel: SubscriberListViewModel by viewModels {
        val appDatabase = AppDatabase.getInstance(requireContext())
        val repository: SubscriberRepository = DatabaseDataSource(appDatabase.subscriberDao)

        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SubscriberListViewModel(repository) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //quando nossa view estiver disponível com todos os recursos e componentes, chamaremos as funções abaixo
        //para tornar o código mais legível é bom deixar o onViewCreated apenas com funções que tenham nomes sugestivos
        observeViewModelEvents()
        configureViewListeners()
    }

    private fun observeViewModelEvents() {
        viewModel.allSubscribersEvent.observe(viewLifecycleOwner, Observer { allSubscribers ->
            val subscriberListAdapter = SubscriberListAdapter(allSubscribers)

            with(recycler_subscribers) {
                //para otimizar o recyclerview, aqui estamos dizendo que todos os itens tem o mesmo tamanho
                setHasFixedSize(true)
                adapter = subscriberListAdapter
            }
        })
    }

    private fun configureViewListeners(){
        fabAddSubscriber.setOnClickListener {
            //Aqui chamamos a função de navegação do navigation controller
            //A view consegue encontrar o findNavController pq está dentro de uma activity que tem configurado o fragment container
            //Desde que as duas telas estejam dentro do mesmo gráfico de navegação podemos utilizar o 'id' dentro do método
            // navigate, podemos conferir apertando ctrl + nome do fragment
            //ps: a forma recomendada(que será visto depois) é fazer a ligação de uma tela para a outra e navegar usando o id dessa
            // 'ação'(<action>)
            findNavController().navigate(R.id.subscriberFragment)
        }
    }
}