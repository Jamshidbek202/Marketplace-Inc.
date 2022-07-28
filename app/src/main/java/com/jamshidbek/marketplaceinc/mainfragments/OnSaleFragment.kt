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
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.utils.adapters.OnSaleRecyclerAdapter
import com.jamshidbek.marketplaceinc.utils.models.ItemModel
import com.jamshidbek.marketplaceinc.utils.models.UserModel

class OnSaleFragment : Fragment() {

    lateinit var btn_categories : ImageView
    lateinit var products_recycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_sale, container, false)
        initialize(view)

        val firestore_db = FirebaseFirestore.getInstance()
        val productList = arrayListOf<ItemModel>()

        firestore_db.collection("OnSale").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if (error != null){
                    Toast.makeText(context, ""+error.message.toString(), Toast.LENGTH_SHORT).show()
                } else {

                    for (dc : DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            productList.add(dc.document.toObject(ItemModel::class.java))
                        }
                    }

                    val adapter = OnSaleRecyclerAdapter(productList, container?.context!!)
                    products_recycler.adapter = adapter
                }
            }
        })

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
        products_recycler = view.findViewById(R.id.on_sale_recycler)
    }

}