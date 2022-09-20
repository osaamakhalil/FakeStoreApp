package com.example.fakestoreapp.presentation.products.viewstate

import com.example.fakestoreapp.presentation.products.model.ProductsUiModel

sealed class ProductsViewState {

    object Error : ProductsViewState()

    object Loading : ProductsViewState()

    object NoInternet : ProductsViewState()

    data class Success(val product: List<ProductsUiModel>) : ProductsViewState()
}
