package com.example.fakestoreapp.data.repository

import com.example.fakestoreapp.data.mapper.ProductMapper
import com.example.fakestoreapp.domain.model.Product
import com.example.fakestoreapp.domain.repository.ProductRepository
import com.example.fakestoreapp.remote.api.ProductApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(
    private val productApiService: ProductApiService,
    private val productMapper: ProductMapper
) : ProductRepository {

    override fun getProducts(): Single<List<Product>> {
        return productApiService.getProducts().map { productMapper.mapList(it) }
    }

}