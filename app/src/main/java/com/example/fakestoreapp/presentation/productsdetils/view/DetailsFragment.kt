package com.example.fakestoreapp.presentation.productsdetils.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fakestoreapp.R
import com.example.fakestoreapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        binding.backImageView.setOnClickListener {
           findNavController().popBackStack()
        }
    }

    private fun setupViews() {
        args.apply {
            binding.priceTextView.text = productDetails.price.toString()
            binding.titleTextView.text = productDetails.title
            binding.descriptionTextView.text = productDetails.description
            binding.ratingTextView.text = productDetails.rate.toString()
        }
        Glide.with(binding.root.context)
            .load(args.productDetails.image)
            .error(R.drawable.default_image)
            .into(binding.detailsImageView)
    }

}