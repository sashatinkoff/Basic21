package com.isidroid.b21.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.isidroid.b21.MainViewModel
import com.isidroid.b21.di.viewmodel.ViewModelFactory
import com.isidroid.b21.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds @IntoMap @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}