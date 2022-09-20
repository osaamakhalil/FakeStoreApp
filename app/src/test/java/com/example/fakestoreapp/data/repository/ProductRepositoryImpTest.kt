package com.example.fakestoreapp.data.repository

import com.example.fakestoreapp.data.mapper.ProductMapper
import com.example.fakestoreapp.data.mapper.RatingMapper
import com.example.fakestoreapp.remote.api.ProductApiService
import com.example.fakestoreapp.remote.model.ProductRemote
import com.example.fakestoreapp.remote.model.RatingRemote
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class ProductRepositoryImpTest {

    private val api = mock<ProductApiService>()
    private val ratingMapper = RatingMapper()
    private val productMapper = ProductMapper(ratingMapper)

    private val productRepositoryImp =
        ProductRepositoryImp(productApiService = api, productMapper = productMapper)

    @Test
    fun `get products will calls api`() {
        stubGetProducts(Single.never())

        productRepositoryImp.getProducts()
        verify(api).getProducts()
    }

    @Test
    fun `get products will return data and completes`() {
        val productListRemote = listOf(
            ProductRemote(
                id = 22,
                title = "test",
                price = 2.2F,
                description = "test",
                image = "test",
                rating = RatingRemote(rate = 2.2F, count = 2)
            )
        )
        stubGetProducts(Single.just(productListRemote))

        val productList = productMapper.mapList(productListRemote)

        val testObserver = productRepositoryImp.getProducts().test()
        testObserver.assertValue(productList)
    }


    private fun stubGetProducts(single: Single<List<ProductRemote>>) {
        whenever(api.getProducts())
            .thenReturn(single)
    }

}