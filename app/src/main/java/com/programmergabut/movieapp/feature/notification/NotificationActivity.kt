package com.programmergabut.movieapp.feature.notification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.programmergabut.core.data.Resource
import com.programmergabut.core.domain.model.Notification
import com.programmergabut.movieapp.databinding.ActivityNotificationBinding
import com.programmergabut.movieapp.databinding.ListNotificationBinding
import com.programmergabut.movieapp.util.PackageUtil
import com.programmergabut.movieapp.util.fadeInAndOut
import com.programmergabut.movieapp.util.stopFadeInAndOut
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity: AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var notificationAdapter: NotificationAdapter

    val viewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        setListener()
        viewModel.getListNotification()
    }

    private fun setupAdapter() {
        notificationAdapter = NotificationAdapter()
        notificationAdapter.listData = mutableListOf()
        binding.rvNotification.apply {
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(this@NotificationActivity,
                LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun setListener() {

        viewModel.notifications.observe(this) {
            when(it){
                is Resource.Loading -> {
                    setLoading(true)
                }
                is Resource.Success -> {
                    setLoading(false)
                    notificationAdapter.listData = it.data?.toMutableList() ?: mutableListOf()
                    notificationAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setLoading(isVisible: Boolean) {
        binding.iLoading.root.isVisible = isVisible
        binding.rvNotification.isVisible = !isVisible
        with(binding.iLoading){
            if(isVisible){
                clTitle1.root.fadeInAndOut()
                tvDesc1.root.fadeInAndOut()
                ivIcon1.root.fadeInAndOut()
                llInfo1.root.fadeInAndOut()
                vDivider1.root.fadeInAndOut()

                clTitle2.root.fadeInAndOut()
                tvDesc2.root.fadeInAndOut()
                ivIcon2.root.fadeInAndOut()
                llInfo2.root.fadeInAndOut()
                vDivider2.root.fadeInAndOut()

                clTitle3.root.fadeInAndOut()
                tvDesc3.root.fadeInAndOut()
                ivIcon3.root.fadeInAndOut()
                llInfo3.root.fadeInAndOut()
                vDivider3.root.fadeInAndOut()

                clTitle4.root.fadeInAndOut()
                tvDesc4.root.fadeInAndOut()
                ivIcon4.root.fadeInAndOut()
                llInfo4.root.fadeInAndOut()
                vDivider4.root.fadeInAndOut()
            } else {
                clTitle1.root.stopFadeInAndOut()
                tvDesc1.root.stopFadeInAndOut()
                ivIcon1.root.stopFadeInAndOut()
                llInfo1.root.stopFadeInAndOut()
                vDivider1.root.stopFadeInAndOut()

                clTitle2.root.stopFadeInAndOut()
                tvDesc2.root.stopFadeInAndOut()
                ivIcon2.root.stopFadeInAndOut()
                llInfo2.root.stopFadeInAndOut()
                vDivider2.root.stopFadeInAndOut()

                clTitle3.root.stopFadeInAndOut()
                tvDesc3.root.stopFadeInAndOut()
                ivIcon3.root.stopFadeInAndOut()
                llInfo3.root.stopFadeInAndOut()
                vDivider3.root.stopFadeInAndOut()

                clTitle4.root.stopFadeInAndOut()
                tvDesc4.root.stopFadeInAndOut()
                ivIcon4.root.stopFadeInAndOut()
                llInfo4.root.stopFadeInAndOut()
                vDivider4.root.stopFadeInAndOut()
            }
        }
    }

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

}