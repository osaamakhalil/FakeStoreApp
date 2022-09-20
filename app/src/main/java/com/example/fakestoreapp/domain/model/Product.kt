package com.example.fakestoreapp.domain.model


data class Product(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val image: String,
    val rating: Rating
)