package com.example.a02crudcomroom.ui.subscriberlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a02crudcomroom.R
import com.example.a02crudcomroom.data.db.entity.SubscriberEntity
import kotlinx.android.synthetic.main.subscriber_list_fragment.*

class SubscriberListFragment : Fragment(R.layout.subscriber_list_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    val subscriberListAdapter = SubscriberListAdapter(
        listOf(
            SubscriberEntity(1,"frozen","frozen@email.com"),
            SubscriberEntity(2,"joabson","joabson@email.com"),
            SubscriberEntity(3,"mel","mel@email.com")
        )
    )
        recycler_subscribers.run {
            //para otimizar o recyclerview, aqui estamos dizendo que todos os itens tem o mesmo tamanho
            setHasFixedSize(true)
            adapter = subscriberListAdapter
        }

    }

}