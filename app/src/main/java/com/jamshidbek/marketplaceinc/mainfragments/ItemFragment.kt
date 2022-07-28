package com.jamshidbek.marketplaceinc.mainfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.databinding.FragmentAddToSaleBinding
import com.jamshidbek.marketplaceinc.databinding.FragmentItemBinding

class ItemFragment : Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.txtItemName.text = arguments?.getString("name")
        binding.txtAboutItemCategory.text = arguments?.getString("category")
        binding.txtAboutItemDesc.text = arguments?.getString("desc")
        binding.txtAboutItemSeller.text = arguments?.getString("seller")
        binding.txtAboutItemLocation.text = arguments?.getString("city")
        binding.txtAboutItemPhone.text = arguments?.getString("phone")

        if (arguments?.getString("currency") == "Dollars"){
            binding.txtAboutItemPrice.text = "$" + arguments?.getString("price")
        } else {
            binding.txtAboutItemPrice.text = arguments?.getString("price") + " " + arguments?.getString("currency")
        }

        Glide.with(this).load(arguments?.getString("imgUrl"))
            .placeholder(R.drawable.ic_add_image)
            .error(R.drawable.ic_add_image)
            .into(binding.imgAboutItemImage)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}