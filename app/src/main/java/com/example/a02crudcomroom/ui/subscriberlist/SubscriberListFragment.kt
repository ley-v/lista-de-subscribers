package com.example.a02crudcomroom.ui.subscriberlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
        observeViewModelEvents()
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
}