package com.programmergabut.movieapp.feature.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.programmergabut.core.domain.model.Notification
import com.programmergabut.movieapp.databinding.ListNotificationBinding

class NotificationAdapter: RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>(){

    var listData = mutableListOf<Notification>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ListNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) =
        holder.bind(listData[position])


    override fun getItemCount(): Int = listData.size

    class NotificationViewHolder(private val binding: ListNotificationBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(data: Notification) {
            with(binding){
                tvTitle.text = data.title
                tvDesc.text = data.dsc
            }
        }
    }
}