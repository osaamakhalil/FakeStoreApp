package com.example.fakestoreapp.presentation.products.mapper

import com.example.fakestoreapp.domain.model.Product
import com.example.fakestoreapp.presentation.products.model.ProductsUiModel
import javax.inject.Inject

class ProductUiMapper @Inject constructor() {

    fun map(product: List<Product>): List<ProductsUiModel> {

        return product.map {
            ProductsUiModel(
                id = it.id,
                image = it.image,
                title = it.title,
                price = it.price
            )
        }
    }
}
