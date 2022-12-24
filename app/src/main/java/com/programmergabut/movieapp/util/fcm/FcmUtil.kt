package com.programmergabut.movieapp.util.fcm

import com.programmergabut.movieapp.feature.notification.NotificationActivity

object FcmUtil {

    const val extraName = "fcm_navigation_extra"

    object NotificationActivityData {
        val activityName: String = NotificationActivity::class.java.name.toString()
        val deepLink: String = "http://movie_app_notification"
        const val requestCode = 999
        const val channelId = "fcm_default_channel"
        const val notificationId = 999
    }
}