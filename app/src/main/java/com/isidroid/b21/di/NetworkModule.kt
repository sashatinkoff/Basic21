package com.isidroid.b21.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.isidroid.b21.network.Api
import com.isidroid.b21.network.ApiFirebaseMessaging
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object NetworkModule {
    private fun <T> httpClient(cl: Class<T>, logLevel: HttpLoggingInterceptor.Level): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(logger(cl = cl, logLevel = logLevel))
            .protocols(listOf(Protocol.HTTP_1_1))

        return builder.build()
    }

    private fun <T> logger(cl: Class<T>, logLevel: HttpLoggingInterceptor.Level) =
        HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.tag(cl.simpleName).i(message)
                }
            }).apply { level = logLevel }

    private fun <T> api(baseUrl: String, cl: Class<T>, logLevel: HttpLoggingInterceptor.Level): T =
        Retrofit.Builder()
            .client(httpClient(cl = cl, logLevel = logLevel))
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()
            .create(cl) as T

    @JvmStatic @Singleton @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .setLenient()
        .create()

    @JvmStatic @Singleton @Provides
    fun provideApi(): Api =
        api(
            baseUrl = "https://jsonplaceholder.typicode.com/",
            cl = Api::class.java,
            logLevel = HttpLoggingInterceptor.Level.BASIC
        )

    @JvmStatic @Singleton @Provides
    fun provideFirebaseMessagingApi(): ApiFirebaseMessaging =
        api(
            baseUrl = "https://fcm.googleapis.com/fcm/",
            cl = ApiFirebaseMessaging::class.java,
            logLevel = HttpLoggingInterceptor.Level.BODY
        )

    @JvmStatic @Singleton @Provides @FirebaseRegistrationTokenId
    fun provideFirebaseRegistrationTokenId() = "AAAAWpJBgk0:APA91bFZlIqbsDfLAJnOEfTWnmvVGfFmckSf4mnuRmAgu53GTkehoXsuUsb0rJ6oY9mDppcwB6B7Gos1HUH3FgbKoQVPo9mLZZVDc4uQIkmKAYvSneobEP_fvgiI0WlqTMkNAlwKbmvx"

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
annotation class FirebaseRegistrationTokenId