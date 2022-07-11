package com.jamshidbek.marketplaceinc.auth

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.jamshidbek.marketplaceinc.MainActivity
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.utils.docs.UserUID
import com.jamshidbek.marketplaceinc.utils.docs.db.DBHelper
import com.jamshidbek.marketplaceinc.utils.models.UserModel

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        var txt_signUp : TextView = findViewById(R.id.txt_signUp)
        val btn_signIn : MaterialButton = findViewById(R.id.btn_signIn)
        val edt_email : EditText = findViewById(R.id.edt_email_login)
        val edt_password : EditText = findViewById(R.id.edt_password_login)

        txt_signUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        btn_signIn.setOnClickListener {
            var txt_email = edt_email.text.toString().trim()
            var txt_password = edt_password.text.toString().trim()

            if (txt_email != "" && android.util.Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()){
                if (txt_password != "" && txt_password.length >= 8){

                    val auth = FirebaseAuth.getInstance()

                    auth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))

                            //writes to the offline database
                            writeToDatabase()

                            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }.addOnFailureListener { e ->
                        Toast.makeText(
                            this, "" + e.message, Toast.LENGTH_SHORT).show()
                    }

                } else{
                    Toast.makeText(this, "Your password is too short!", Toast.LENGTH_SHORT).show()
                    txt_password = ""
                    edt_password.setText(txt_password)
                }
            } else {
                Toast.makeText(this, "Invalid email!", Toast.LENGTH_SHORT).show()
                txt_email = ""
                edt_email.setText(txt_email)
            }
        }
    }

    private fun writeToDatabase() {
        val firestoreDatabase = FirebaseFirestore.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        var model = UserModel()

        firestoreDatabase.collection("Users").document(uid).get().addOnSuccessListener { document ->
            if (document != null){
                model = UserModel(document.get("uid").toString(), document.get("name").toString(), document.get("surname").toString(),
                    document.get("phone").toString(), document.get("email").toString(), document.get("password").toString(), document.get("city").toString(), document.get("imgUrl").toString())

                UserUID.user_uid = model.uid

                //update offline db
                val db = DBHelper(this)
                db.updateData(model)
            }
        }.addOnFailureListener{exception ->
            Toast.makeText(this, ""+exception.message, Toast.LENGTH_SHORT).show()
        }
    }
}