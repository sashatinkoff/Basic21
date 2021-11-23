package com.isidroid.b21.di

import dagger.Module

private const val BASE_API_URL = "https://domain.com/api/v1/"

@Module
object NetworkModule {
//    private fun <T> httpClient(
//        cl: Class<T>,
//        logLevel: HttpLoggingInterceptor.Level,
//        authenticator: Authenticator?
//    ): OkHttpClient {
//        val builder = OkHttpClient().newBuilder()
//            .readTimeout(15, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//
//        authenticator?.also { builder.authenticator(it) }
//        builder.addInterceptor(logger(cl = cl, logLevel = logLevel))
//        return builder.build()
//    }
//
//    private fun <T> logger(cl: Class<T>, logLevel: HttpLoggingInterceptor.Level) =
//        HttpLoggingInterceptor { message -> Timber.tag(cl.simpleName).i(message) }
//            .apply { level = logLevel }
//
//    private fun <T> api(
//        baseUrl: String = BASE_API_URL,
//        cl: Class<T>,
//        logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY,
//        authenticator: Authenticator?
//    ): T = Retrofit.Builder()
//        .client(httpClient(cl = cl, logLevel = logLevel, authenticator = authenticator))
//        .baseUrl(baseUrl)
//        .addConverterFactory(GsonConverterFactory.create(provideGson()))
//        .build()
//        .create(cl) as T
//
//    @JvmStatic @Singleton @Provides
//    fun provideGson(): Gson = GsonBuilder()
//        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//        .setLenient()
//        .create()
//
//    @JvmStatic @Singleton @Provides
//    fun provideAuthInterceptor(sessionUseCase: ISessionUseCase): Authenticator =
//        AuthInterceptor(sessionUseCase)
//
//    @JvmStatic @Singleton @Provides
//    fun provideApiSession(authenticator: Authenticator) = api(
//        baseUrl = BASE_API_URL,
//        cl = ApiSession::class.java,
//        logLevel = HttpLoggingInterceptor.Level.BODY,
//        authenticator = authenticator
//    )

}