package com.jamshidbek.marketplaceinc.mainfragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.utils.docs.UserUID
import com.jamshidbek.marketplaceinc.utils.docs.db.DBHelper

class EditProfileFragment : Fragment() {

    lateinit var edit_name : TextView
    lateinit var edit_surname : TextView
    lateinit var edit_phone : TextView
    lateinit var edit_profile_image : ImageView
    lateinit var at_cities : AutoCompleteTextView
    lateinit var btn_update : ImageView
    lateinit var edit_photo : CardView
    lateinit var edit_profile_pic : ImageView
    lateinit var progressBar : ProgressBar
    lateinit var imgUrl : String

    var pressed = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        initialize(view)

        val db = DBHelper(container!!.context)
        val model = db.getAllData()

        edit_name.text = model.name
        edit_surname.text = model.surname
        edit_phone.text = model.phone
        val cities = resources.getStringArray(R.array.cities)
        val arrayAdapter = ArrayAdapter(container.context, R.layout.drop_down_item, cities)
        at_cities.setAdapter(arrayAdapter)
        Glide.with(this).load(model.imgUrl)
            .placeholder(R.drawable.ic_account_default)
            .error(R.drawable.ic_account_default)
            .into(edit_profile_image)

        edit_photo.setOnClickListener {
            openGalleryForImage()
            pressed = 1
        }

        btn_update.setOnClickListener {
            model.name = edit_name.text.toString()
            model.surname = edit_surname.text.toString()
            model.phone = edit_phone.text.toString()
            model.city = at_cities.text.toString()

            if(pressed == 0){
                imgUrl = model.imgUrl
            } else {
                model.imgUrl = imgUrl
            }
            db.updateData(model)

            val firestoreDB = FirebaseFirestore.getInstance()
            firestoreDB.collection("Users").document(model.uid).set(model, SetOptions.merge())
            Toast.makeText(container.context, "Updated!", Toast.LENGTH_SHORT).show()
            val navOption = NavOptions.Builder()
            navOption.setEnterAnim(R.anim.enter_anim)
            navOption.setPopExitAnim(R.anim.exit_anim)
            val bundle = bundleOf("something" to "something")

            view.findNavController().navigate(R.id.profileFragment, bundle, navOption.build())
        }

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
            edit_profile_image.setImageURI(data.data)
            val filePath = data.data

            //upload to cloud
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference

            progressBar.isVisible = true
            storageRef.child("${UserUID.user_uid}/profilePic").putFile(filePath!!).addOnSuccessListener {
                    Toast.makeText(context, "Uploaded successfully.", Toast.LENGTH_SHORT).show()

                    storageRef.child( "${UserUID.user_uid}/profilePic").downloadUrl.addOnSuccessListener { downloadUrl ->
                        imgUrl = downloadUrl.toString()
                        val fdb = FirebaseFirestore.getInstance().collection("Users").document(UserUID.user_uid)
                        fdb.update("imgUrl", downloadUrl.toString())
                        progressBar.isVisible = false
                    }.addOnFailureListener { exception ->
                        Toast.makeText(context, "Exception: "+exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show() }
        }
    }

    private fun initialize(view: View) {
        edit_name = view.findViewById(R.id.edit_txt_profile_name)
        edit_surname = view.findViewById(R.id.edit_txt_profile_surname)
        edit_phone = view.findViewById(R.id.edit_txt_phone_number)
        edit_profile_image = view.findViewById(R.id.edit_img_profile)
        at_cities = view.findViewById(R.id.edit_choose_city)
        btn_update = view.findViewById(R.id.btn_save_edit)
        edit_photo = view.findViewById(R.id.edit_profile_pic_card)
        progressBar = view.findViewById(R.id.progressBar)
    }
}