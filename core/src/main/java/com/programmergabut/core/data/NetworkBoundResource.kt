package com.programmergabut.core.data

import android.annotation.SuppressLint
import com.programmergabut.core.data.remote.network.ApiResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = PublishSubject.create<Resource<ResultType>>()
    private val mCompositeDisposable = CompositeDisposable()

    init {
        try {
            @Suppress("LeakingThis")
            val dbSource = loadFromDB()
            val db = dbSource
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ value ->
                    dbSource.unsubscribeOn(Schedulers.io())
                    if (shouldFetch(value)) {
                        fetchFromNetwork()
                    } else {
                        result.onNext(Resource.Success(value))
                    }
                },  {
                    if (shouldFetch(null)) {
                        fetchFromNetwork()
                    } else {
                        result.onNext(Resource.Error(it.localizedMessage ?: ""))
                    }
                })
            mCompositeDisposable.add(db)
        } catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flowable<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): Flowable<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    @SuppressLint("CheckResult")
    private fun fetchFromNetwork() {

        val apiResponse = createCall()

        result.onNext(Resource.Loading(null))
        val response = apiResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe ({ response ->
                when (response) {
                    is ApiResponse.Success -> {
                        saveCallResult(response.data)
                        val dbSource = loadFromDB()
                        dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe ({
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Success(it))
                            }, {
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Error(it.localizedMessage ?: ""))
                            })
                    }
                    is ApiResponse.Empty -> {
                        val dbSource = loadFromDB()
                        dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe ({
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Success(it))
                            }, {
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Error(it.localizedMessage ?: ""))
                            })
                    }
                    is ApiResponse.Error -> {
                        onFetchFailed()
                        val dbSource = loadFromDB()
                        dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe ({
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Success(it))
                            }, {
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Error(it.localizedMessage ?: ""))
                            })
                    }
                }
            }, {
                result.onNext(Resource.Error(it.localizedMessage ?: ""))
            })
        mCompositeDisposable.add(response)
    }

    fun asFlowable(): Flowable<Resource<ResultType>> =
        result.toFlowable(BackpressureStrategy.BUFFER)
}