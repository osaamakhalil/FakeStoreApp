package com.example.fakestoreapp.domain.repository

import com.example.fakestoreapp.domain.model.Product
import io.reactivex.rxjava3.core.Single

interface ProductRepository {

    fun getProducts(): Single<List<Product>>

}