package com.vault.videostube.di

import android.arch.lifecycle.ViewModelProvider
import com.vault.videostube.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindsViewModel(factory: ViewModelFactory): ViewModelProvider.Factory
}