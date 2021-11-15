package com.example.a02crudcomroom.ui.subscriber

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a02crudcomroom.R
import com.example.a02crudcomroom.repository.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    //Aqui estamos encapsulando o código para que, por exemplo, na view não seja possível chamar o 'value' dessa propriedade
    //Foi então definido dois eventos, o '_subscriberStateEventData' pra notificar quando um usuário foi inserido -vai ser chamado
    // o inserted da sealed class SubscriberState; e quando tiver algum erro no catch será chamado o messageEventData para poder
    // chamar um evento de mensagem para a view para mostrar ao usuário
    private val _subscriberStateEventData = MutableLiveData<SubscriberState>()
    val subscriberStateEventData: LiveData<SubscriberState>
        get() = _subscriberStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun addOrUpdateSubscriber(name: String, email: String, id: Long = 0) {
        //se o id passado for maior que 0 significa que se trata de uma atualização, senão trata-se de uma inserção
        if (id > 0) {
            updateSubscribe(id, name, email)
        } else {
            insertSubscribe(name, email)
        }
    }

    private fun updateSubscribe(id: Long, name: String, email: String) = viewModelScope.launch {
        try {
            repository.updateSubscriber(id, name, email)
            _subscriberStateEventData.value = SubscriberState.Updated
            _messageEventData.value = R.string.subscriber_updated_successfully


        } catch (ex: Exception) {
            _messageEventData.value = R.string.subscriber_error_to_insert
            Log.e(TAG, ex.toString())
        }
    }

    //    fun addSubscribe(name: String, email: String) {  ---renomeado para:
    private fun insertSubscribe(name: String, email: String) {
        //essa é uma extension do lifecycle. Aqui iniciamos um escopo de coroutines com ciclo de vida, que é gerenciado
        // automaticamente pelo viewmodel e pela view. Assim não precisamos nos preocupar em ficar limpando a memória quando o
        // app é destruído ou colocado em background, por ex.
        //Como estamos usando coroutines, conseguimos utilizar as features da própria linguagem como o try/catch, já que o coroutines
        // é uma feature do kotlin
        viewModelScope.launch {
            try {
                val id = repository.insertSubscriber(name, email)
                if (id > 0) {
                    //Aqui dentro da verificação, se um subscriber for inserido com sucesso no banco de dados, vamos chamar o
                    // '_subscriberStateEventData' e no seu value chamar o 'SubscriberState' e passar o inserted para a view saber
                    // que um evento de uma inserção aconteceu e então a view reagirá /-de um jeito que será posteriormente
                    // programado-/ ~ /-a view vai decidir como irá tratar este evento internamente-/
                    _subscriberStateEventData.value = SubscriberState.Inserted
                    //por último chamaremos o '_messageEventData' e em seu value passaremos a string que definimos na string.xml
                    _messageEventData.value = R.string.subscriber_inserted_successfully
                }
            } catch (e: Exception) {
                //caso aconteça algum erro iremos otificar o usuário
                _messageEventData.value = R.string.subscriber_error_to_insert
                Log.e(TAG, e.toString())
            }
        }
    }

    //Essa sealed class foi criada para lidar com os estados de uma forma mais fácil. Com ela definimos apenas um ponto para
    // observar as notificações dos eventos, e olhando o código podemos perceber em qual ponto estamos trabalhando com o estado
    // de inserção/atualização/delete etc. Ou seja, a sealed class foi criada para representar o estado da view
    sealed class SubscriberState {
        object Inserted : SubscriberState()
        object Updated : SubscriberState()
    }

    companion object {
        //TAG = SubscriberViewModel
        private val TAG = SubscriberViewModel::class.java.simpleName
    }

}