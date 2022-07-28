package com.jamshidbek.marketplaceinc.mainfragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.databinding.FragmentAddToSaleBinding
import com.jamshidbek.marketplaceinc.utils.docs.UserUID
import com.jamshidbek.marketplaceinc.utils.docs.db.DBHelper
import com.jamshidbek.marketplaceinc.utils.models.ItemModel
import java.net.URI

class AddToSaleFragment : Fragment() {

    private var _binding: FragmentAddToSaleBinding? = null
    private val binding get() = _binding!!

    lateinit var filePath: Uri
    var pressed = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddToSaleBinding.inflate(inflater, container, false)
        val view = binding.root

        val categories = resources.getStringArray(R.array.categories)
        val arrayAdapter =
            ArrayAdapter(requireActivity().applicationContext, R.layout.drop_down_item, categories)
        binding.edtCategory.setAdapter(arrayAdapter)

        val currencies = resources.getStringArray(R.array.currency)
        val currencyAdapter =
            ArrayAdapter(requireActivity().applicationContext, R.layout.drop_down_item, currencies)
        binding.actCurrency.setAdapter(currencyAdapter)

        val cities = resources.getStringArray(R.array.cities)
        val cityAdapter =
            ArrayAdapter(requireActivity().applicationContext, R.layout.drop_down_item, cities)
        binding.actCity.setAdapter(cityAdapter)

        binding.addImageCard.setOnClickListener {
            openGalleryForImage()
            pressed = 1
        }

        binding.btnPost.setOnClickListener {
            val view = View.inflate(context, R.layout.progress_layout, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
            var path = ""

            if (pressed == 0) {
                filePath = path.toUri()
                Toast.makeText(context, "Please add an image!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                val name = binding.edtItemName.text.toString()
                val desc = binding.edtItemDesc.text.toString()
                val category = binding.edtCategory.text.toString()
                val price = binding.edtItemPrice.text.toString()
                val currency = binding.actCurrency.text.toString()
                val city = binding.actCity.text.toString()
                val phone = binding.edtItemPhone.text.toString()

                if(binding.actCurrency.text.toString() == "" || name == "" || desc == "" || category == "Choose your category:" || price == "" || city == "Choose your city:" || phone == "" ){
                    Toast.makeText(context, "Fill in the info!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                } else {

                    //upload to cloud
                    val dbHelper = DBHelper(container!!.context)
                    val seller = dbHelper.getAllData().name + " " + dbHelper.getAllData().surname

                    val storage = FirebaseStorage.getInstance()
                    val storageRef = storage.reference

                    storageRef.child("${UserUID.user_uid}/itemPics/${name}").putFile(filePath)
                        .addOnSuccessListener {

                            storageRef.child("${UserUID.user_uid}/itemPics/${name}").downloadUrl.addOnSuccessListener { downloadUrl ->
                                path = downloadUrl.toString()

                                if (path == "") {
                                    Toast.makeText(context, "Image not added!", Toast.LENGTH_SHORT).show()
                                } else {
                                    val model = ItemModel(UserUID.user_uid, seller, name, path, desc, category, price, currency, city, phone)
                                    val db = FirebaseFirestore.getInstance()
                                    db.collection("OnSale").document(name).set(model)
                                    db.collection("Users").document(UserUID.user_uid).collection("UsersProducts").document(name).set(model)
                                    Toast.makeText(context, "Product added!", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()

                                    //return to home
                                    binding.imgAddPicture.setImageResource(R.drawable.ic_add_image)
                                    binding.edtItemName.setText("")
                                    binding.edtItemDesc.setText("")
                                    binding.edtCategory.setText(R.string.choose_your_category)
                                    binding.edtItemPrice.setText("")
                                    binding.actCurrency.setText("")
                                    binding.actCity.setText(R.string.choose_your_city)
                                    binding.edtItemPhone.setText("")
                                }
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }
                }
            }
        }

        return view
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 777)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 777) {
            if (data == null || data.data == null) {
                return
            }
            binding.imgAddPicture.setImageURI(data.data)
            filePath = data.data!!
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}