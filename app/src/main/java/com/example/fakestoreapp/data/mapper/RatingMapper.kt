package com.example.fakestoreapp.data.mapper

import com.example.fakestoreapp.domain.model.Rating
import com.example.fakestoreapp.remote.model.RatingRemote
import javax.inject.Inject

class RatingMapper @Inject constructor() {
    fun map(remote: RatingRemote): Rating {

        assertEssentialParams(remote)

        return Rating(
            rate = remote.rate!!,
            count = remote.count!!
        )
    }

    private fun assertEssentialParams(remote: RatingRemote) {
        when {
            remote.rate == null -> {
                throw RuntimeException("The params: ProductRemote.rate is missing in received object: $remote")

            }
            remote.count == null -> {
                throw RuntimeException("The params: ProductRemote.count is missing in received object: $remote")
            }
        }
    }
}