package com.isidroid.b21.di

import android.content.Context
import com.isidroid.b21.clean.data.TakePictureInteractor
import com.isidroid.b21.clean.data.picturehandler.PictureHandlerInteractor
import com.isidroid.b21.clean.domain.IPictureHandlerUseCase
import com.isidroid.b21.clean.domain.ITakePictureUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object ImageModule {
    @JvmStatic @Singleton @Provides @PictureAuthority
    fun providePictureAuthority(context: Context) =
        "${context.packageName}.fileprovider"

    @JvmStatic @Singleton @Provides
    fun providePictureHandlerUseCase(
        @PictureAuthority authority: String,
        takePictureUseCase: ITakePictureUseCase
    ): IPictureHandlerUseCase = PictureHandlerInteractor(authority, takePictureUseCase)

    @JvmStatic @Singleton @Provides
    fun provideTakePictureUseCase(context: Context): ITakePictureUseCase =
        TakePictureInteractor(context)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
annotation class PictureAuthority