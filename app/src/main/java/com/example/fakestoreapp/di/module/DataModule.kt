package com.example.fakestoreapp.di.module

import com.example.fakestoreapp.data.repository.ProductRepositoryImp
import com.example.fakestoreapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {
    @Binds
    abstract fun bindProductRepository(repositoryImp: ProductRepositoryImp): ProductRepository
}