package com.example.fakestoreapp.presentation.products.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fakestoreapp.domain.model.Product
import com.example.fakestoreapp.domain.usecase.GetProductUseCase
import com.example.fakestoreapp.presentation.products.mapper.ProductUiMapper
import com.example.fakestoreapp.presentation.products.viewstate.ProductsViewAction
import com.example.fakestoreapp.presentation.products.viewstate.ProductsViewEvent
import com.example.fakestoreapp.presentation.products.viewstate.ProductsViewState
import com.example.fakestoreapp.presentation.productsdetils.mapper.ProductDetailsUiMapper
import com.example.fakestoreapp.utils.Event
import com.example.fakestoreapp.utils.NetworkChecker
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val productsUiMapper: ProductUiMapper,
    private val detailsUiMapper: ProductDetailsUiMapper,
    private val networkChecker: NetworkChecker,
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _productsViewState = MutableLiveData<ProductsViewState>()
    val productsViewState: LiveData<ProductsViewState>
        get() = _productsViewState

    private val _viewEvent = MutableLiveData<Event<ProductsViewEvent>>()
    val viewEvent: LiveData<Event<ProductsViewEvent>>
        get() = _viewEvent

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var productsList: List<Product>? = null

    init {
        ProductsViewState.Loading.also(_productsViewState::setValue)
        getProducts()
    }

    fun postAction(action: ProductsViewAction) {
        when (action) {
            is ProductsViewAction.ClickOnProduct -> handleClickOnProduct(action.id)
            is ProductsViewAction.ClickOnTryAgain -> getProducts()
        }
    }

    private fun handleClickOnProduct(id: Int) {
        val product = productsList?.find { it.id == id }
        product?.let {
            val productDetailsUiModel = detailsUiMapper.map(it)
            _viewEvent.postValue(Event(ProductsViewEvent.ClickOnProduct(productDetailsUiModel)))
        }
    }

    private fun getProducts() {
        _productsViewState.postValue(ProductsViewState.Loading)
        if (networkChecker.isConnectedToInternet()) {
            getProductUseCase
                .execute()
                .subscribe({ onGetProductSuccess(it) },
                    { onProductError(it) })
                .also(compositeDisposable::add)
        } else if (productsList == null) {
            _productsViewState.postValue(ProductsViewState.NoInternet)
        } else {
            _viewEvent.postValue(Event(ProductsViewEvent.NoInternet))
        }
    }

    private fun onGetProductSuccess(products: List<Product>) {
        productsList = products
        _productsViewState.postValue(ProductsViewState.Success(productsUiMapper.map(products)))

    }

    private fun onProductError(error: Throwable) {
        _productsViewState.postValue(ProductsViewState.Error)
        Timber.e("Something went wrong when loading product list: $error")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}