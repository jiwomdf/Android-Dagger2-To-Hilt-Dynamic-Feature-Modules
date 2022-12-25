package com.programmergabut.movieapp.util

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

object PermissionUtil {

    private fun AppCompatActivity.requestPermissionLauncher(
        isNeedEducateMessage: Boolean,
        onSuccess:() -> Unit,
        onNeedGrantPermission:() -> Unit,
        onNeedEducationMessage: () -> Unit
    ): ActivityResultLauncher<String> {
        return registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                onSuccess.invoke()
            } else {
                if(!isNeedEducateMessage){
                    onNeedGrantPermission.invoke()
                } else {
                    onNeedEducationMessage.invoke()
                }
            }
        }
    }

    fun AppCompatActivity.askNotificationPermission(
        onSuccess:() -> Unit,
        onNeedGrantPermission:() -> Unit,
        onNeedEducationMessage:() -> Unit
    ) {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher(
                    isNeedEducateMessage =
                        shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS),
                    onSuccess = {
                        onSuccess.invoke()
                    },
                    onNeedGrantPermission = {
                        onNeedGrantPermission.invoke()
                    },
                    onNeedEducationMessage = {
                        onNeedEducationMessage.invoke()
                    }
                ).launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}