package com.example.a02crudcomroom.ui.subscriberlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a02crudcomroom.data.db.entity.SubscriberEntity
import com.example.a02crudcomroom.repository.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberListViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {

    //não é necessário criar uma função. Podemos usar uma variável
    //Supostamente não era pra atualização automática do recyclerview funcionar, já que para ver as alterações em tempo real
    // com esse liveData nós tinhamos que ter tudo na mesma tela, ou seja, se o cadastro de subscribers fosse feito com um
    // dialogBox ao invés de abrir um novo fragment era pra funcionar pq estaria no mesmo contexto. Mas quando abre-se uma
    // tela nova, ela fica em cima da anterior, o que faz com que a tela anterior entre no ciclo de vida onStop, e com isso
    // o evento de liveData não consegue escutar. Em tese o liveData só funciona quando se está na mesma tela e se faz uma
    // alteração no banco de dados, e assim ele consegue atualizar automaticamente a lista. (Entretanto, funcionou)
//    val allSubscribersEvent = repository.getAllSubscriber()

    //agora, ao invés de receber a notificação automaticamente quando o banco de dados for alterado, teremos que chamar o
    // método 'getAllSubscribers' toda vez que quisermos ver alguma atualização na tela
    private val _allSubscribersEvent = MutableLiveData<List<SubscriberEntity>>()
    val allSubscribersEvent: LiveData<List<SubscriberEntity>>
        get() = _allSubscribersEvent

    fun getAllSubscribers() {
        //para chamar uma função suspend precisamos iniciar um escopo de coroutines
        viewModelScope.launch {
            _allSubscribersEvent.postValue(repository.getAllSubscriber())
        }
    }
}