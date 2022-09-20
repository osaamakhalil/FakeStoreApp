package com.example.fakestoreapp.presentation.products.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fakestoreapp.MyApplication
import com.example.fakestoreapp.R
import com.example.fakestoreapp.databinding.FragmentProductsBinding
import com.example.fakestoreapp.di.ViewModelProviderFactory
import com.example.fakestoreapp.presentation.products.adapter.ProductAdapter
import com.example.fakestoreapp.presentation.products.viewmodel.ProductsViewModel
import com.example.fakestoreapp.presentation.products.viewstate.ProductsViewAction
import com.example.fakestoreapp.presentation.products.viewstate.ProductsViewEvent
import com.example.fakestoreapp.presentation.products.viewstate.ProductsViewState
import com.example.fakestoreapp.presentation.productsdetils.view.DetailsFragmentArgs
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: ProductAdapter

    @Inject
    lateinit var viewModeFactory: ViewModelProviderFactory
    private val viewModel: ProductsViewModel by viewModels { viewModeFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).provideAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productAdapter = ProductAdapter { id ->
            viewModel.postAction(ProductsViewAction.ClickOnProduct(id = id))
        }
        handleViewState()
        handleViewEvent()
        tryAgainButton()
    }

    private fun handleViewState() {
        viewModel.productsViewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ProductsViewState.Error -> {
                    serverErrorView(true)
                    tryAgainView(true)
                    progressbarView(false)
                    noInternetView(false)
                }
                is ProductsViewState.Loading -> {
                    progressbarView(true)
                    tryAgainView(false)
                    noInternetView(false)
                    serverErrorView(false)
                }
                is ProductsViewState.NoInternet -> {
                    tryAgainView(true)
                    noInternetView(true)
                    progressbarView(false)
                    serverErrorView(false)
                }
                is ProductsViewState.Success -> {
                    tryAgainView(false)
                    progressbarView(false)
                    serverErrorView(false)
                    noInternetView(false)
                    productAdapter.submitList(viewState.product)
                    binding.productsRecyclerView.adapter = productAdapter
                }
            }
        }
    }

    private fun handleViewEvent() {
        viewModel.viewEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { viewEvent ->
                when (viewEvent) {
                    is ProductsViewEvent.ClickOnProduct -> {
                        findNavController().navigate(
                            R.id.detailsFragment,
                            DetailsFragmentArgs(viewEvent.productDetailsUiModel).toBundle()
                        )
                    }
                    ProductsViewEvent.NoInternet -> {
                        Snackbar.make(
                            binding.root,
                            "Theres no Internet Connection",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun tryAgainButton() {
        binding.btTryAgain.setOnClickListener {
            viewModel.postAction(ProductsViewAction.ClickOnTryAgain)
        }
    }

    /*
 * handle views visibility
 *  */
    private fun progressbarView(showViews: Boolean) {
        binding.postProgressBar.isVisible = showViews
    }

    private fun noInternetView(showViews: Boolean) {
        binding.tvNoInternet.isVisible = showViews
    }

    private fun serverErrorView(showViews: Boolean) {
        binding.ivServerError.isVisible = showViews
    }

    private fun tryAgainView(showViews: Boolean) {
        binding.btTryAgain.isVisible = showViews
    }


}