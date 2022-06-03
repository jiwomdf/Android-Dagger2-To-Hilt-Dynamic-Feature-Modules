package com.programmergabut.movieapp.feature.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.programmergabut.movieapp.App
import com.programmergabut.movieapp.R
import com.programmergabut.movieapp.databinding.ActivityMainBinding
import com.programmergabut.movieapp.util.PackageUtil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
        loadFragment(HomeFragment.newInstance())
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
                else -> {}
            }
            false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.fragment_fade_enter,
            R.anim.fragment_fade_exit,
            R.anim.fragment_fade_enter,
            R.anim.fragment_fade_exit
        )
        transaction.replace(R.id.fcv_main, fragment, fragment.javaClass.toString())
        transaction.commit()
    }

}