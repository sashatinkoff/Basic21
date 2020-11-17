package com.isidroid.b21.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

//    @Binds @IntoMap @ViewModelKey(PostsViewModel::class)
//    abstract fun bindMainViewModel(viewModel: PostsViewModel): ViewModel
}