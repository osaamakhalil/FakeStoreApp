package com.example.fakestoreapp.data.mapper

import com.example.fakestoreapp.domain.model.Product
import com.example.fakestoreapp.remote.model.ProductRemote
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun mapList(remote: List<ProductRemote>): List<Product> {
        assertEssentialParams(remote)

        return remote.map {
            Product(
                title = it.title!!,
                price = it.price!!,
                description = it.description!!,
                image = it.image!!,
                rating = it.rating!!
            )
        }
    }

    private fun assertEssentialParams(remote: List<ProductRemote>) {
        remote.forEach {
            when {
                it.title == null -> {
                    throw RuntimeException("The params: ProductRemote.title is missing in received object: $it")
                }
                it.price == null -> {
                    throw RuntimeException("The params: ProductRemote.price is missing in received object: $it")
                }
                it.description == null -> {
                    throw RuntimeException("The params: ProductRemote.description is missing in received object: $it")
                }
                it.image == null -> {
                    throw RuntimeException("The params: ProductRemote.image is missing in received object: $it")
                }
                it.rating == null -> {
                    throw RuntimeException("The params: ProductRemote.image is missing in received object: $remote")
                }
            }
        }
    }

}