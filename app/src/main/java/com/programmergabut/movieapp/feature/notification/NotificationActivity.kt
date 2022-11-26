package com.programmergabut.movieapp.feature.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmergabut.core.data.Resource
import com.programmergabut.core.domain.model.Notification
import com.programmergabut.movieapp.base.BaseActivity
import com.programmergabut.movieapp.databinding.ActivityNotificationBinding
import com.programmergabut.movieapp.databinding.ListNotificationBinding
import com.programmergabut.movieapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity: BaseActivity<ActivityNotificationBinding>() {

    private lateinit var notificationAdapter: NotificationAdapter

    val viewModel: NotificationViewModel by viewModels()

    override fun getViewBinding(): ActivityNotificationBinding =
        ActivityNotificationBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdapter()
        setListener()
        setTransparentStatusBar(binding.root)
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
            showFadeLoading(clTitle1.root, null, isVisible)
            showFadeLoading(tvDesc1.root, null, isVisible)
            showFadeLoading(ivIcon1.root, null, isVisible)
            showFadeLoading(llInfo1.root, null, isVisible)
            showFadeLoading(vDivider1.root, null, isVisible)

            showFadeLoading(tvDesc2.root, null, isVisible)
            showFadeLoading(ivIcon2.root, null, isVisible)
            showFadeLoading(llInfo2.root, null, isVisible)
            showFadeLoading(vDivider2.root, null, isVisible)

            showFadeLoading(tvDesc3.root, null, isVisible)
            showFadeLoading(ivIcon3.root, null, isVisible)
            showFadeLoading(llInfo3.root, null, isVisible)
            showFadeLoading(vDivider3.root, null, isVisible)

            showFadeLoading(tvDesc4.root, null, isVisible)
            showFadeLoading(ivIcon4.root, null, isVisible)
            showFadeLoading(llInfo4.root, null, isVisible)
            showFadeLoading(vDivider4.root, null, isVisible)
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