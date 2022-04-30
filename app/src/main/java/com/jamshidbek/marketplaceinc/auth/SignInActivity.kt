package com.jamshidbek.marketplaceinc.auth

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.jamshidbek.marketplaceinc.MainActivity
import com.jamshidbek.marketplaceinc.R

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
}