package com.example.fakestoreapp.di.module

import com.example.fakestoreapp.remote.api.ProductApiService
import dagger.Module
import retrofit2.Retrofit

@Module
abstract class RemoteModule {
    companion object {
        fun provideProductApiService(retrofit: Retrofit): ProductApiService {
            return retrofit.create(ProductApiService::class.java)
        }
    }

}