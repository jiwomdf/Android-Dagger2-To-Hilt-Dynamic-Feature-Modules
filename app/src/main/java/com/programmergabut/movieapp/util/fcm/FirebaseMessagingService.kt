package com.programmergabut.movieapp.util.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.programmergabut.movieapp.R
import com.programmergabut.movieapp.feature.main.MainActivity

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        val title = remoteMessage.notification?.title ?: ""
        val body = remoteMessage.notification?.body ?: ""
        val data = remoteMessage.data

        /**
         * the isUserLogin code is to avoid notifying the user if he already logout
         * you can get the user is login based from your app flow,
         * it could be from sharedPreference, database, api, or other data source
         * */
        val isUserLogin = true

        if(isUserLogin){
            sendNotification(title = title, description = body, data = data)
        }
    }

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    private fun sendNotification(title: String, description: String, data: MutableMap<String, String>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val daa = data["fcm_navigation_extra"] ?: ""
        intent.putExtra(FcmUtil.extraName, daa)

        val pendingIntent = PendingIntent.getActivity(
            this,
            FcmUtil.NotificationActivityData.requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(
            this,
            FcmUtil.NotificationActivityData.channelId
        )
            .setContentTitle(title)
            .setContentText(description)
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE) //Important for heads-up notification
            .setPriority(Notification.PRIORITY_HIGH) //Important for heads-up notification
            .setAutoCancel(true)
            .setSmallIcon(getNotificationIcon())
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setColor(ContextCompat.getColor(this@FirebaseMessagingService, R.color.black))


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                FcmUtil.NotificationActivityData.channelId,
                "notification_activity",
                NotificationManager.IMPORTANCE_HIGH  //Important for heads-up notification
            )
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(
            FcmUtil.NotificationActivityData.notificationId,
            notificationBuilder.build()
        )
    }

    private fun getNotificationIcon(): Int = R.drawable.menu_notification_24

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

}
