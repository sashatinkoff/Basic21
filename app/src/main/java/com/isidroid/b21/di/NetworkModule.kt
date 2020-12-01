package com.isidroid.b21.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.isidroid.b21.network.AuthInterceptor
import com.isidroid.b21.network.apis.ApiTest
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

private const val BASE_API_URL = "https://domain.com/api/v1/"

@Module
object NetworkModule {
    private fun <T> httpClient(
        cl: Class<T>,
        logLevel: HttpLoggingInterceptor.Level,
        authInterceptor: Interceptor?
    ): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        authInterceptor?.let { builder.addInterceptor(it) }
        builder.addInterceptor(logger(cl = cl, logLevel = logLevel))

        return builder.build()
    }

    private fun <T> logger(cl: Class<T>, logLevel: HttpLoggingInterceptor.Level) =
        HttpLoggingInterceptor { message -> Timber.tag(cl.simpleName).i(message) }
            .apply { level = logLevel }

    private fun <T> api(
        baseUrl: String = BASE_API_URL,
        cl: Class<T>,
        logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY,
        authInterceptor: Interceptor? = null
    ): T = Retrofit.Builder()
        .client(httpClient(cl = cl, logLevel = logLevel, authInterceptor = authInterceptor))
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
    fun provideApiImgur() = api(
        baseUrl = BASE_API_URL,
        cl = ApiTest::class.java,
        logLevel = HttpLoggingInterceptor.Level.BODY,
        authInterceptor = AuthInterceptor()
    )

}