package com.example.fakestoreapp.presentation.products.viewstate

import com.example.fakestoreapp.presentation.productsdetils.model.ProductDetailsUiModel

sealed class ProductsViewEvent {
    data class ClickOnProduct(val productDetailsUiModel: ProductDetailsUiModel) :
        ProductsViewEvent()

    object NoInternet : ProductsViewEvent()
}