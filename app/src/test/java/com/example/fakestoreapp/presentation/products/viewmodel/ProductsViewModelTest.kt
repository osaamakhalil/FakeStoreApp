package com.example.fakestoreapp.presentation.products.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fakestoreapp.domain.model.Product
import com.example.fakestoreapp.domain.model.Rating
import com.example.fakestoreapp.domain.usecase.GetProductUseCase
import com.example.fakestoreapp.presentation.products.mapper.ProductUiMapper
import com.example.fakestoreapp.presentation.products.viewstate.ProductsViewAction
import com.example.fakestoreapp.presentation.products.viewstate.ProductsViewEvent
import com.example.fakestoreapp.presentation.products.viewstate.ProductsViewState
import com.example.fakestoreapp.presentation.productsdetils.mapper.ProductDetailsUiMapper
import com.example.fakestoreapp.utils.NetworkChecker
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class ProductsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val getProductUseCase = mock<GetProductUseCase>()
    private val productUiMapper = ProductUiMapper()
    private val productDetailsUiMapper = ProductDetailsUiMapper()
    private val networkChecker = mock<NetworkChecker>()
    private val viewModel: ProductsViewModel by lazy {
        ProductsViewModel(
            getProductUseCase = getProductUseCase,
            productsUiMapper = productUiMapper,
            detailsUiMapper = productDetailsUiMapper,
            networkChecker = networkChecker

        )
    }

    @Test
    fun `init view model will emit loading state when internet is connected`() {
        stubNetworkChecker(true)
        stubGetProductUseCase(Single.never())

        assertEquals(ProductsViewState.Loading, viewModel.productsViewState.value)
    }

    @Test
    fun `init view model will emit no internet state when internet isn't connected`() {
        stubNetworkChecker(false)
        stubGetProductUseCase(Single.never())

        verifyZeroInteractions(getProductUseCase)
        assertEquals(ProductsViewState.NoInternet, viewModel.productsViewState.value)
    }

    @Test
    fun `view model starts with getting list of products will emit success view state`() {
        stubNetworkChecker(true)

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

        stubGetProductUseCase(Single.just(productList))

        val product = productUiMapper.map(productList)
        assertEquals(ProductsViewState.Success(product), viewModel.productsViewState.value)
    }

    @Test
    fun `view model updates view state with error when error occurs while fetching the data`() {
        stubNetworkChecker(true)
        stubGetProductUseCase(Single.error(Throwable()))

        assertEquals(ProductsViewState.Error, viewModel.productsViewState.value)
    }


    @Test
    fun `action click on product emits click on product view event `() {
        stubNetworkChecker(true)

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
        stubGetProductUseCase(Single.just(productList))

        val productDetailsUiModel = productDetailsUiMapper.map(
            Product(
                id = 22,
                title = "test",
                price = 2.2F,
                description = "test",
                image = "test",
                rating = Rating(rate = 2.2F, count = 2)
            )
        )
        viewModel.postAction(ProductsViewAction.ClickOnProduct(22))

        assertEquals(
            ProductsViewEvent.ClickOnProduct(productDetailsUiModel),
            viewModel.viewEvent.value?.peekContent()
        )
    }

    @Test
    fun `action try again call use case to fetch the list of products when internet is connected`() {
        stubNetworkChecker(true)

        stubGetProductUseCase(Single.never())

        viewModel.postAction(ProductsViewAction.ClickOnTryAgain)
        verify(getProductUseCase)
    }

    @Test
    fun `post action try again won't call use case when internet isn't connected`() {
        stubNetworkChecker(false)

        stubGetProductUseCase(Single.never())

        viewModel.postAction(ProductsViewAction.ClickOnTryAgain)
        verifyZeroInteractions(getProductUseCase)
    }

    private fun stubNetworkChecker(isConnected: Boolean) {
        whenever(networkChecker.isConnectedToInternet())
            .thenReturn(isConnected)
    }

    private fun stubGetProductUseCase(single: Single<List<Product>>) {
        whenever(getProductUseCase.execute())
            .thenReturn(single)
    }

}