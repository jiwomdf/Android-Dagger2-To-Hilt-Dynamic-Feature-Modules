package com.programmergabut.movieapp.feature.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.programmergabut.movieapp.R
import com.programmergabut.movieapp.base.BaseActivity
import com.programmergabut.movieapp.databinding.ActivityMainBinding
import com.programmergabut.movieapp.feature.notification.NotificationActivity
import com.programmergabut.movieapp.util.PackageUtil
import com.programmergabut.movieapp.util.setTransparentStatusBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
        loadFragment(HomeFragment.newInstance())
        setTransparentStatusBar(binding.root)
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

}