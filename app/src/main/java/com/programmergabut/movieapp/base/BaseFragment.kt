package com.programmergabut.movieapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.programmergabut.core.domain.prefs.Prefs
import com.programmergabut.movieapp.util.setStatusBarThemeMode
import javax.inject.Inject

abstract class BaseFragment<VB: ViewBinding>: Fragment() {

    @Inject
    lateinit var prefs: Prefs

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    lateinit var binding : VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        return binding.root
    }

}