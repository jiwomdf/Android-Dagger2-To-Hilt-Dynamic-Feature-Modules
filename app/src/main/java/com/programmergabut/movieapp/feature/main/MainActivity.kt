package com.programmergabut.movieapp.feature.main

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.programmergabut.movieapp.R
import com.programmergabut.movieapp.base.BaseActivity
import com.programmergabut.movieapp.databinding.ActivityMainBinding
import com.programmergabut.movieapp.feature.notification.NotificationActivity
import com.programmergabut.movieapp.util.PackageUtil
import com.programmergabut.movieapp.util.PermissionUtil.askNotificationPermission
import com.programmergabut.movieapp.util.fcm.FcmUtil
import com.programmergabut.movieapp.util.setTransparentStatusBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private val TAG = "MainActivity"
    }

    val viewModel: MainViewModel by viewModels()

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
        loadFragment(HomeFragment.newInstance())
        setTransparentStatusBar(binding.root)
        setupRedirectionFromFcm(intent)

        askNotificationPermission(
            onSuccess = {},
            onNeedGrantPermission = {
                Toast.makeText(this@MainActivity,
                    "permission is needed for receiving the notification",
                    Toast.LENGTH_SHORT).show()
            },
            onNeedEducationMessage = {
                Toast.makeText(this@MainActivity,
                    "you can active the notification from setting",
                    Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun setTitle(title: String) {
        binding.tbMain.title = title
    }

    private fun setListener() {
        binding.tbMain.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_favorites -> {
                    Intent(this, Class.forName(PackageUtil.FavMovieActivity.reflection)).also {
                        startActivity(it)
                    }
                }
                R.id.menu_notification -> {
                    Intent(this, NotificationActivity::class.java).also {
                        startActivity(it)
                    }
                }
                R.id.menu_theme -> {
                    if(prefs.isDarkThemeMode){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    prefs.isDarkThemeMode = !prefs.isDarkThemeMode
                }
                else -> {}
            }
            false
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e(TAG,  "Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }

            /**
             *  Get new FCM registration token
             **/
            val token = task.result
            Log.e(TAG, "fcm token: $token", )

            /**
             *  insert / update firebase token,
             *  it should in the same row with your unique identity
             *  in this example it's username, because we will
             *  send the notification based on it username
             **/
            val username = "test-user"
            viewModel.updateFcmToken(
                uname = username,
                token = token,
            )
        })
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        transaction.replace(R.id.fcv_main, fragment, fragment.javaClass.toString())
        transaction.commit()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setupRedirectionFromFcm(intent)
    }

    private fun setupRedirectionFromFcm(intent: Intent) {
        val deepLink = intent.extras?.getString(FcmUtil.extraName)
        if(!deepLink.isNullOrEmpty()){
            val redirectionIntent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLink))
            redirectionIntent.convertToSafeDynamicFeatureModuleIntent(this)
            startActivity(redirectionIntent)
        }
    }

    private fun Intent.convertToSafeDynamicFeatureModuleIntent(context: Context) {
        val options = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.queryIntentActivities(this, PackageManager.ResolveInfoFlags.of(
                PackageManager.MATCH_DEFAULT_ONLY.toLong()))
        } else {
            context.packageManager.queryIntentActivities(this, PackageManager.MATCH_DEFAULT_ONLY)
        }
        setClassName(packageName, options[0].activityInfo.name)
    }

}