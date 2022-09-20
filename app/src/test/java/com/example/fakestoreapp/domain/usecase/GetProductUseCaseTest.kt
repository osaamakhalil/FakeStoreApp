package com.example.fakestoreapp.domain.usecase

import com.example.fakestoreapp.domain.model.Product
import com.example.fakestoreapp.domain.model.Rating
import com.example.fakestoreapp.domain.repository.ProductRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetProductUseCaseTest {

    private val repository = mock<ProductRepository>()
    private val getProductUseCase = GetProductUseCase(repository = repository)

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
    }

    @Test
    fun `execute use case will calls repository`() {
        stubGetProducts(Single.never())

        getProductUseCase.execute()
        verify(repository).getProducts()
    }

    @Test
    fun `execute use case will success and return data`() {
        val productList = listOf(
            Product(
                id = 22,
                title = "test",
                price = 2.2F,
                description = "test",
                image = "test",
                rating = Rating(rate = 2.2F, count = 2)
            )
        )

        stubGetProducts(Single.just(productList))

        val testObserver = getProductUseCase.execute().test()
        testObserver.assertValueAt(0, productList)
    }

    @Test
    fun `execute use case with error will emit error`() {
        val error = Throwable()

        stubGetProducts(Single.error(error))

        val testObserver = getProductUseCase.execute().test()
        testObserver.assertError(error)
    }


    private fun stubGetProducts(single: Single<List<Product>>) {
        whenever(repository.getProducts())
            .thenReturn(single)
    }
}