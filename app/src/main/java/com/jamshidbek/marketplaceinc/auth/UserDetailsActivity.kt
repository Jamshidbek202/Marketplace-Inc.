package com.jamshidbek.marketplaceinc.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.jamshidbek.marketplaceinc.MainActivity
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.utils.docs.db.DBHelper
import com.jamshidbek.marketplaceinc.utils.models.UserModel

class UserDetailsActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val firestoreDatabase = FirebaseFirestore.getInstance()

        val txt_email = intent.getStringExtra("email").toString()
        val txt_password = intent.getStringExtra("password").toString()
        val btnFinish = findViewById<MaterialButton>(R.id.btn_finish)
        val edt_name = findViewById<EditText>(R.id.edt_name)
        val edt_surname = findViewById<EditText>(R.id.edt_surname)
        val edt_phone = findViewById<EditText>(R.id.edt_phone)
        val dd_city = findViewById<AutoCompleteTextView>(R.id.dd_choose_region)
        val cities = resources.getStringArray(R.array.cities)
        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.drop_down_item, cities)
        val ddMenu = findViewById<AutoCompleteTextView>(R.id.dd_choose_region)
        ddMenu.setAdapter(arrayAdapter)

        btnFinish.setOnClickListener {
            val txt_name = edt_name.text.toString().trim()
            val txt_surname = edt_surname.text.toString().trim()
            val txt_phone = edt_phone.text.toString().trim()
            val txt_city = dd_city.text.toString().trim()

            if(txt_name != "" && txt_surname != "" && txt_phone != "" && txt_city != ""){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        val firebaseUser : FirebaseUser = task.result!!.user!!
                        var uid = firebaseUser.uid
                        var model = UserModel(uid, txt_email, txt_password, txt_name, txt_surname, txt_phone, txt_city)

                        firestoreDatabase.collection("Users").document(uid).set(model, SetOptions.merge())

                        val mDatabase = FirebaseDatabase.getInstance().getReference()

                        mDatabase.child("Users").child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                //writes to the online database
                                snapshot.ref.child("uid").setValue(uid)
                                snapshot.ref.child("email").setValue(txt_email)
                                snapshot.ref.child("password").setValue(txt_password)
                                snapshot.ref.child("name").setValue(txt_name)
                                snapshot.ref.child("surname").setValue(txt_surname)
                                snapshot.ref.child("phone").setValue(txt_phone)
                                snapshot.ref.child("city").setValue(txt_city)

                                //writes to the offline database
                                val model = UserModel(uid, txt_email, txt_password, txt_name, txt_surname, txt_phone, txt_city)
                                val dbHelper = DBHelper(this@UserDetailsActivity)
                                dbHelper.addData(model)
                                Toast.makeText(this@UserDetailsActivity, "Data added to the database", Toast.LENGTH_SHORT).show()

                                Toast.makeText(this@UserDetailsActivity, "Registered successfully!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@UserDetailsActivity, MainActivity::class.java)
                                intent.putExtra("uid", uid)
                                startActivity(intent)
                                finish()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(this@UserDetailsActivity, error.message, Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                    if (task.isCanceled){
                        Toast.makeText(this, "An error occurred!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Fill in all the information", Toast.LENGTH_SHORT).show()
            }
        }
    }
}