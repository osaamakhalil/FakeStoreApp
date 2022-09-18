package com.example.fakestoreapp.domain.model

import com.example.fakestoreapp.remote.model.RatingRemote

data class Product(
    val title: String,
    val price: Float,
    val description: String,
    val image: String,
    val rating: RatingRemote
)