package com.example.a02crudcomroom.ui.subscriber

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a02crudcomroom.R
import com.example.a02crudcomroom.data.db.AppDatabase
import com.example.a02crudcomroom.data.db.dao.SubscriberDao
import com.example.a02crudcomroom.extension.hideKeyboard
import com.example.a02crudcomroom.repository.DatabaseDataSource
import com.example.a02crudcomroom.repository.SubscriberRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.subscriber_fragment.*

//passando a referência de layout diretamente para não precisar do onCreateView apenas para inflar o layout
//class SubscriberFragment : Fragment() {
class SubscriberFragment : Fragment(R.layout.subscriber_fragment) {


    //o 'by viewModels' é da extensions do lifecycle
    private val viewModel: SubscriberViewModel by viewModels {
        //objeto anônimo
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                //'requireContext' para pegar o contexto do fragment
                val subscriberDao: SubscriberDao =
                    AppDatabase.getInstance(requireContext()).subscriberDao
                val repository: SubscriberRepository = DatabaseDataSource(subscriberDao)
                return SubscriberViewModel(repository) as T
            }

        }
    }

    //o 'onCreateView' apenas retorna uma view inflada. Se não for necessário colocar nenhum código de configuração no onCreateView,
    // podemos simplemente pegar a referência de layout e passá-la diretamente como argumento do fragment
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.subscriber_fragment, container, false)
//    }


    //o 'onViewCreated' é executado depois que a view tiver sido criada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //essa função irá observar os eventos do viewModel/ escutar os liveDatas
        observeEvents()
        //é aqui dentro que iremos definir os listeners das views
        setListeners()
    }


    //privada pq apenas o fragment terá acesso
    private fun observeEvents() {
    //devemos passar o 'viewLifecycleOwner' para o melhor gerenciamento do ciclo de vida
        viewModel.subscriberStateEventData.observe(viewLifecycleOwner){ subscriberState: SubscriberViewModel.SubscriberState ->
    //para trabalhar com o sealed class(padrão mvi) agora é mais fácil pois só temos que manipular uma única função, e dentro desta
    // nós temos um panorama geral dos eventos da nossa aplicação, do que está acontecendo. Aqui vamos começar a escutar aqueles
    // estados
            when(subscriberState){
                //quando o estado desse evento for do tipo 'SubscriberViewModel.SubscriberState.Inserted'
                is SubscriberViewModel.SubscriberState.Inserted -> {
                    //após uma inserção com sucesso vamos limpar os campos
                    clearFields()
                    //esse código forçará o teclado desaparecer quando voltar para a tela de listagem de usuário
                    hideKeyboard()
                }
            }

        }
        viewModel.messageEventData.observe(viewLifecycleOwner){ stringResId ->
            Snackbar.make(requireView(), stringResId,Snackbar.LENGTH_LONG).show()
//            Toast.makeText(activity, "a", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        input_name.text?.clear()
        input_email.text?.clear()
    }

    private fun hideKeyboard() {
        //como poderá ser útil usar esse código em várias partes do app, criaremos uma extensions
        //em primeiro lugar vamos recuperar a activity. Com o 'requireActivity' obtemos a instância da activity
        val parentActivity = requireActivity()
        //verificamos de o 'parentActivity' é do tipo 'AppCompatActivity' para podermos chamar a extensions
        if(parentActivity is AppCompatActivity){
            //ps: todas as extensões convém ser criadas numa pasta específica
            parentActivity.hideKeyboard()
        }

    }

    private fun setListeners() {
        //vamos escutar a função de click do botão de adicionar subscribers. Dessa forma estaremos enviando os dados para o VM
        //e assim sucessivamente até chegar na camada de dados
        button_subscriber.setOnClickListener {
            val name = input_name.text.toString()
            val email = input_email.text.toString()

        viewModel.addSubscribe(name, email)
        }

    }

}