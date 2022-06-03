package com.programmergabut.core.di

import com.programmergabut.core.data.remote.network.MovieApiService
import com.programmergabut.core.utils.Constant.BASE_URL
import com.programmergabut.core.utils.Constant.TMDB_API_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    private val hostname = "api.themoviedb.org"
    private val certificatePinner = CertificatePinner.Builder()
        .add(hostname,"sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
        .add(hostname,"sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
        .add(hostname,"sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(Interceptor { chain ->
                val request = chain.request()
                val newUrl = request.url.newBuilder()
                    .addQueryParameter("api_key", TMDB_API_KEY)
                    .addQueryParameter("language", Locale.getDefault().language)
                    .build()

                val newRequest = request.newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            })
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApiService(client: OkHttpClient): MovieApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(MovieApiService::class.java)
    }
}