package com.jamshidbek.marketplaceinc.mainfragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.jamshidbek.marketplaceinc.R
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

        if (arguments?.getString("currency") == "Dollars") {
            binding.txtAboutItemPrice.text = "$" + arguments?.getString("price")
        } else {
            binding.txtAboutItemPrice.text =
                arguments?.getString("price") + " " + arguments?.getString("currency")
        }

        Glide.with(this).load(arguments?.getString("imgUrl"))
            .placeholder(R.drawable.ic_add_image)
            .error(R.drawable.ic_add_image)
            .into(binding.imgAboutItemImage)

        binding.btnContactTheOwner.setOnClickListener {
            makePhoneCall()
        }

        return view
    }

    private fun makePhoneCall() {
        val number: String = binding.txtAboutItemPhone.text.toString()
        if (number.trim { it <= ' ' }.isNotEmpty()) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), 777)
            } else {
                val dial = "tel:$number"
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            }
        } else {
            Toast.makeText(context, "No number found!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == 777) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
                Toast.makeText(context, "Permission GRANTED", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}