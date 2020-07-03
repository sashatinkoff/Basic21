package com.isidroid.b21.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.isidroid.b21.sample.Api
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class NetworkModule {
    private fun <T> httpClient(cl: Class<T>, logLevel: HttpLoggingInterceptor.Level) =
        OkHttpClient().newBuilder()
            .addInterceptor(logger(cl = cl, logLevel = logLevel))
            .build()

    private fun <T> logger(cl: Class<T>, logLevel: HttpLoggingInterceptor.Level) =
        HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.tag(cl.simpleName).i(message)
                }
            }).apply { level = logLevel }

    private fun <T> api(baseUrl: String, cl: Class<T>): T = Retrofit.Builder()
        .client(httpClient(cl = cl, logLevel = HttpLoggingInterceptor.Level.BASIC))
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .build()
        .create(cl) as T

   @Singleton @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .setLenient()
        .create()

    @Singleton @Provides fun provideApi(): Api =
        api(baseUrl = "https://jsonplaceholder.typicode.com/", cl = Api::class.java)

}