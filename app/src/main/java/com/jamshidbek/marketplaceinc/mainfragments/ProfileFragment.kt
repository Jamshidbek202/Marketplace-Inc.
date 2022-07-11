package com.jamshidbek.marketplaceinc.mainfragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.utils.docs.UserUID
import com.jamshidbek.marketplaceinc.utils.docs.db.DBHelper
import java.io.File
import java.util.*


class ProfileFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    var uid = UserUID.user_uid

    lateinit var name : TextView
    lateinit var surname : TextView
    lateinit var phone : TextView
    lateinit var city : TextView
    lateinit var profile_image : ImageView
    lateinit var add_image_card : CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initialize(view)

        add_image_card.setOnClickListener {
            openGalleryForImage()
        }

        val db = DBHelper(container!!.context)
        val model = db.getAllData()

        name.text = model.name
        surname.text = model.surname
        phone.text = model.phone
        city.text = model.city
        Glide.with(this).load(model.imgUrl)
            .placeholder(R.drawable.ic_account_default)
            .error(R.drawable.ic_account_default)
            .into(profile_image);

        return view
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 111){
            if(data == null || data.data == null){
                return
            }
            profile_image.setImageURI(data.data)
            val filePath = data.data

            //upload to cloud
            val file = File(java.lang.String.valueOf(filePath))
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference

            storageRef.child("images/" + file.name).putFile(filePath!!)
                .addOnSuccessListener {
                    Toast.makeText(context, "Uploaded successfully.", Toast.LENGTH_SHORT).show()

                    storageRef.child("images/" + file.name).downloadUrl.addOnSuccessListener { downloadUrl ->
                        val fdb = FirebaseFirestore.getInstance().collection("Users").document(uid)
                        fdb.update("imgUrl", downloadUrl.toString())
                    }.addOnFailureListener { exception ->
                        Toast.makeText(context, "Exception: "+exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show() }
        }
    }

    private fun initialize(view: View) {
        name = view.findViewById(R.id.txt_profile_name)
        surname = view.findViewById(R.id.txt_profile_surname)
        phone = view.findViewById(R.id.txt_phone_number)
        city = view.findViewById(R.id.txt_city)
        profile_image = view.findViewById(R.id.img_profile)
        add_image_card = view.findViewById(R.id.profile_pic_card)
    }
}