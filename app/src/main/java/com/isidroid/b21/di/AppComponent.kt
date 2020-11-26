package com.isidroid.b21.di

import android.content.Context
import com.isidroid.b21.clean.presentation.chat.ChatFragment
import com.isidroid.b21.clean.presentation.login.LoginFragment
import com.isidroid.b21.clean.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        ViewModelModule::class,
        AppModule::class,
        ImageModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: ChatFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}