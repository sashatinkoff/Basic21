package com.isidroid.b21.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.isidroid.b21.network.ApiChat
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val REDDIT_API_URL = "https://api.reddit.com/api/v1/"
private const val REDDIT_URL = "https://www.reddit.com/"

@Module
object NetworkModule {
    private fun <T> httpClient(
        cl: Class<T>,
        logLevel: HttpLoggingInterceptor.Level,
        redditInterceptor: Interceptor?
    ): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        redditInterceptor?.let { builder.addInterceptor(it) }
        builder.addInterceptor(logger(cl = cl, logLevel = logLevel))

        return builder.build()
    }

    private fun <T> logger(cl: Class<T>, logLevel: HttpLoggingInterceptor.Level) =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Timber.tag(cl.simpleName).i(message)
        })
            .apply { level = logLevel }

    private fun <T> api(
        baseUrl: String = REDDIT_API_URL,
        cl: Class<T>,
        logLevel: HttpLoggingInterceptor.Level,
        authInterceptor: Interceptor? = null
    ): T = Retrofit.Builder()
        .client(httpClient(cl = cl, logLevel = logLevel, redditInterceptor = authInterceptor))
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .build()
        .create(cl) as T

    @JvmStatic
    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .setLenient()
        .create()

    @JvmStatic
    @Singleton
    @Provides
    fun provideApiChat() = api(
        baseUrl = "https://fcm.googleapis.com/fcm/",
        cl = ApiChat::class.java,
        logLevel = HttpLoggingInterceptor.Level.BODY
    )

}