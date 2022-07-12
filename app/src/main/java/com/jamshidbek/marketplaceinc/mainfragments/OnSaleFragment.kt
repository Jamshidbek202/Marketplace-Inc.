package com.jamshidbek.marketplaceinc.mainfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.jamshidbek.marketplaceinc.R

class OnSaleFragment : Fragment() {

    lateinit var btn_categories : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_sale, container, false)
        initialize(view)

        btn_categories.setOnClickListener {
            val navOption = NavOptions.Builder()
            navOption.setEnterAnim(R.anim.enter_anim)
            navOption.setPopExitAnim(R.anim.exit_anim)
            val bundle = bundleOf("something" to "something")

            view.findNavController().navigate(R.id.categoriesFragment, bundle, navOption.build())
        }

        return view
    }

    private fun initialize(view: View) {
        btn_categories = view.findViewById(R.id.btn_categories)
    }

}