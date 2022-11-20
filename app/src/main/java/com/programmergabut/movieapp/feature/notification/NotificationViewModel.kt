package com.programmergabut.movieapp.feature.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.programmergabut.core.data.Resource
import com.programmergabut.core.domain.model.Movie
import com.programmergabut.core.domain.model.Notification
import com.programmergabut.core.domain.usecase.MovieUseCase
import com.programmergabut.movieapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val useCase: MovieUseCase): BaseViewModel() {

    private val _notifications = MutableLiveData<Resource<List<Notification>>>()
    val notifications = _notifications as LiveData<Resource<List<Notification>>>
    fun getListNotification(){
        collect(
            useCase.getListNotification()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _notifications.postValue(Resource.Loading())
                }
                .subscribe({ response ->
                    _notifications.postValue(Resource.Success(response))
                }, { error ->
                    _notifications.postValue(Resource.Error(error.localizedMessage ?: ""))
                })
        )
    }
}