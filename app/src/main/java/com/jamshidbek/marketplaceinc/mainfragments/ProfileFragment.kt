package com.jamshidbek.marketplaceinc.mainfragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.utils.docs.UserUID
import com.jamshidbek.marketplaceinc.utils.docs.db.DBHelper

class ProfileFragment : Fragment() {

    var uid = UserUID.user_uid

    lateinit var name : TextView
    lateinit var surname : TextView
    lateinit var phone : TextView
    lateinit var city : TextView
    lateinit var profile_image : ImageView
    lateinit var add_image_card : CardView
    lateinit var btnEdit : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initialize(view)

        val db = DBHelper(container!!.context)
        val model = db.getAllData()

        name.text = model.name
        surname.text = model.surname
        phone.text = model.phone
        city.text = model.city
        Glide.with(this).load(model.imgUrl)
            .placeholder(R.drawable.ic_account_default)
            .error(R.drawable.ic_account_default)
            .into(profile_image)

        btnEdit.setOnClickListener {
            val navOption = NavOptions.Builder()
            navOption.setEnterAnim(R.anim.enter_anim)
            navOption.setPopExitAnim(R.anim.exit_anim)
            val bundle = bundleOf("something" to "something")

            view.findNavController().navigate(R.id.editProfileFragment, bundle, navOption.build())
        }

        return view
    }



    private fun initialize(view: View) {
        name = view.findViewById(R.id.edt_txt_profile_name)
        surname = view.findViewById(R.id.edt_txt_profile_surname)
        phone = view.findViewById(R.id.edt_txt_phone_number)
        city = view.findViewById(R.id.edt_txt_city)
        profile_image = view.findViewById(R.id.edt_img_profile)
        add_image_card = view.findViewById(R.id.edt_profile_pic_card)
        btnEdit = view.findViewById(R.id.btn_edt_profile)
    }
}