package com.example.fakestoreapp.presentation.productsdetils.mapper

import com.example.fakestoreapp.domain.model.Product
import com.example.fakestoreapp.presentation.productsdetils.model.ProductDetailsUiModel
import javax.inject.Inject

class ProductDetailsUiMapper @Inject constructor() {

    fun map(product: Product): ProductDetailsUiModel {

        return ProductDetailsUiModel(
            image = product.image,
            title = product.title,
            price = product.price,
            rate = product.rating.rate,
            description = product.description
        )
    }
}