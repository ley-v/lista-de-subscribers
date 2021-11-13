package com.example.a02crudcomroom.ui.subscriberlist

import androidx.lifecycle.ViewModel
import com.example.a02crudcomroom.repository.SubscriberRepository

class SubscriberListViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {

    //não é necessário criar uma função. Podemos usar uma variável
    val allSubscribersEvent = repository.getAllSubscriber()
}