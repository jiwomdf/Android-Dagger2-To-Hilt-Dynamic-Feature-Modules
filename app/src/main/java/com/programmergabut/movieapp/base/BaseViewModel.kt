package com.programmergabut.movieapp.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        detach()
    }

    private fun detach(){
        disposable.dispose()
        disposable.clear()
    }

    fun collect(s: Disposable) {
        disposable.add(s)
    }

}