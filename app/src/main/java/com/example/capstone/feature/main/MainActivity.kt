package com.example.capstone.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstone.App
import com.example.capstone.R
import com.example.capstone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment.newInstance())

        binding.bnbMain.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> loadFragment(HomeFragment.newInstance())
                R.id.menu_favorites -> loadFragment(FavoriteFragment.newInstance())
                else -> {}
            }
            true
        }
    }

    fun setTitle(title: String) {
        binding.tbMain.title = title
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