package com.example.fakestoreapp.remote.model

import com.google.gson.annotations.SerializedName


data class ProductRemote(
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Float,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("rating")
    val rating: Rating
)
