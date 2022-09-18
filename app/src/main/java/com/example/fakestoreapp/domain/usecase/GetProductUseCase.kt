package com.example.fakestoreapp.domain.usecase

import com.example.fakestoreapp.domain.model.Product
import com.example.fakestoreapp.domain.repository.ProductRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    fun execute(): Single<List<Product>> {
        return repository.getProducts()
            .subscribeOn(Schedulers.io())
    }
}