package com.example.fakestoreapp.presentation.productsdetils.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetailsUiModel(
    val image: String,
    val title: String,
    val description: String,
    val price: Float,
    val rate: Float
) : Parcelable