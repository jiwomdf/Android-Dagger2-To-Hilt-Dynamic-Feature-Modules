package com.programmergabut.movieapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.programmergabut.core.domain.prefs.Prefs
import com.programmergabut.movieapp.util.setStatusBarThemeMode
import javax.inject.Inject

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    @Inject
    lateinit var prefs: Prefs

    private lateinit var _binding : VB
    val binding: VB get() {
        if(::_binding.isInitialized) return _binding
        else _binding = getViewBinding()
        return _binding
    }

    abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
        setStatusBarThemeMode(prefs.isDarkThemeMode)
    }
}