package com.example.fakestoreapp.presentation.products.viewstate

sealed class ProductsViewAction {
    data class ClickOnProduct(val id: Int) : ProductsViewAction()

    object ClickOnTryAgain : ProductsViewAction()
}
