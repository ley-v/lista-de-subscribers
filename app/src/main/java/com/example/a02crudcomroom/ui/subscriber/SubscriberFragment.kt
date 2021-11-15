package com.example.a02crudcomroom.ui.subscriber

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

    //o 'SubscriberFragmentArgs' foi gerado automaticamente assim como o directions pelo xml do navigate
    //com essa implementação iremos automaticamente receber o objeto passado como argumento na tela anterior
    private val args: SubscriberFragmentArgs by navArgs()

    //o 'onViewCreated' é executado depois que a view tiver sido criada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //aqui preencheremos o campo de texto com os valores recebidos pelo 'SubscriberFragmentArgs'
        //somente se o args não for nulo, que é o caso do click ser feito num item da lista e não no fab, o que seria o caso
        // de um update, que preencheremos os campos
        args.subscriberArgument?.let { subscriber ->
            //caso seja um update mudaremos o nome do botão 'Add' para 'Update'
            button_subscriber.text = getString(R.string.subscriber_button_update)
            input_name.setText(subscriber.name)
            input_email.setText(subscriber.email)
        }

        //essa função irá observar os eventos do viewModel/ escutar os liveDatas
        observeEvents()
        //é aqui dentro que iremos definir os listeners das views
        setListeners()
    }


    //privada pq apenas o fragment terá acesso
    private fun observeEvents() {
        //devemos passar o 'viewLifecycleOwner' para o melhor gerenciamento do ciclo de vida
        viewModel.subscriberStateEventData.observe(viewLifecycleOwner) { subscriberState: SubscriberViewModel.SubscriberState ->
            //para trabalhar com o sealed class(padrão mvi) agora é mais fácil pois só temos que manipular uma única função, e dentro desta
            // nós temos um panorama geral dos eventos da nossa aplicação, do que está acontecendo. Aqui vamos começar a escutar aqueles
            // estados
            when (subscriberState) {
                //quando o estado desse evento for do tipo 'SubscriberViewModel.SubscriberState.Inserted'
                is SubscriberViewModel.SubscriberState.Inserted,
                is SubscriberViewModel.SubscriberState.Updated
                -> {
                    //após uma inserção com sucesso vamos limpar os campos
                    clearFields()
                    //esse código forçará o teclado desaparecer quando voltar para a tela de listagem de usuário
                    hideKeyboard()

                    requireView().requestFocus()
                    //para voltar a tela anterior utilizando o componente de navegação
                    findNavController().popBackStack()
                }
            }

        }
        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_LONG).show()
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
        if (parentActivity is AppCompatActivity) {
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

            //sempre que houver um valor no args ele será repassado, senão, utilizando o elvis operator(operador ternário?),
            // se for nulo passaremos o valor 0
            viewModel.addOrUpdateSubscriber(name, email, args.subscriberArgument?.id ?: 0)
        }

    }

}