package com.example.a02crudcomroom.ui.subscriberlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a02crudcomroom.R
import com.example.a02crudcomroom.data.db.entity.SubscriberEntity
import kotlinx.android.synthetic.main.subscriber_item.view.*

class SubscriberListAdapter(
    private val subscribers: List<SubscriberEntity>
) :
    RecyclerView.Adapter<SubscriberListAdapter.SubscriberListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberListViewHolder {
        //inflar é o processo de pegar um layout em xml e transfomá-lo em um objeto em kotlin/java
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.subscriber_item, parent, false)
        return SubscriberListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubscriberListViewHolder, position: Int) {
        holder.bindView(subscribers[position])
    }

    override fun getItemCount(): Int = subscribers.size

    //O item é o subscriber_item
    class SubscriberListViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val tvSubscriberName = item.text_subscriber_name
        private val tvSubscriberEmail = item.text_subscriber_email
        fun bindView(subscriber: SubscriberEntity) {
            tvSubscriberName.text = subscriber.name
            tvSubscriberEmail.text = subscriber.email
        }
    }

}