package com.jamshidbek.marketplaceinc.mainfragments

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.*
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.databinding.FragmentAddToSaleBinding
import com.jamshidbek.marketplaceinc.databinding.FragmentUserPostsBinding
import com.jamshidbek.marketplaceinc.utils.adapters.OnSaleRecyclerAdapter
import com.jamshidbek.marketplaceinc.utils.docs.UserUID
import com.jamshidbek.marketplaceinc.utils.models.ItemModel

class UserPostsFragment : Fragment() {

    private var _binding: FragmentUserPostsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserPostsBinding.inflate(inflater, container, false)
        val view = binding.root

        val db = FirebaseFirestore.getInstance()
        val productList = arrayListOf<ItemModel>()

        db.collection("Users").document(UserUID.user_uid).collection("UsersProducts")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null) {
                        Toast.makeText(context, "Error: " + error.message.toString(), Toast.LENGTH_SHORT).show()
                        binding.txtNoPosts.visibility = View.VISIBLE
                        binding.userPostsRecycler.visibility = View.GONE
                    } else {

                        for (dc: DocumentChange in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                productList.add(dc.document.toObject(ItemModel::class.java))
                            }
                        }

                        val adapter = OnSaleRecyclerAdapter(productList, container?.context!!)
                        binding.userPostsRecycler.adapter = adapter

                    }
                }
            })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}