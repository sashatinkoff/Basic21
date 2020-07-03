package com.isidroid.b21.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.isidroid.b21.App
import com.isidroid.b21.MainActivity
import com.isidroid.b21.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(
    modules = [
        NetworkModule::class,
        ViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
}

fun Context.appComponent() = (applicationContext as App).appComponent
fun Fragment.appComponent() = requireActivity().appComponent()