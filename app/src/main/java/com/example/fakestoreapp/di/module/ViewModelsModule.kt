package com.example.fakestoreapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.fakestoreapp.di.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

class ViewModelsModule {
    @Module
    abstract class ViewModelsModule {

        @Binds
        abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory?): ViewModelProvider.Factory?
    }
}