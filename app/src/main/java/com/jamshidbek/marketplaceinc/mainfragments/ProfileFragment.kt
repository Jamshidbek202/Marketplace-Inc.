package com.jamshidbek.marketplaceinc.mainfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.utils.docs.UserUID

class ProfileFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    var uid = UserUID.user_uid

    lateinit var name : TextView
    lateinit var surname : TextView
    lateinit var phone : TextView
    lateinit var city : TextView
    lateinit var profile_image : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initialize(view)

        val documentReference = db.collection("Users").document(uid)
        documentReference.get()
            .addOnSuccessListener { document ->
                if (document != null){
                    name.text  = document.data?.get("name").toString()
                    surname.text = document.data?.get("surname").toString()
                    phone.text = document.data?.get("phone").toString()
                    city.text = document.data?.get("city").toString()
                }
            }
            .addOnFailureListener{ exception ->
                Toast.makeText(context, ""+exception.message, Toast.LENGTH_SHORT).show()
            }

        return view
    }

    private fun initialize(view: View) {
        name = view.findViewById(R.id.txt_profile_name)
        surname = view.findViewById(R.id.txt_profile_surname)
        phone = view.findViewById(R.id.txt_phone_number)
        city = view.findViewById(R.id.txt_city)
    }
}