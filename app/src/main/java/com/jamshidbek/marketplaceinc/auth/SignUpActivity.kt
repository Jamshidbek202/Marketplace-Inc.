package com.jamshidbek.marketplaceinc.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TestLooperManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.jamshidbek.marketplaceinc.R
import org.w3c.dom.Text

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        val txt_to_sign_in = findViewById<TextView>(R.id.txt_signIn)
        val edt_email = findViewById<EditText>(R.id.edt_email_sign_up)
        val edt_password = findViewById<EditText>(R.id.edt_password_sign_up)
        val edt_password_repeat = findViewById<EditText>(R.id.edt_password_repeat)
        val btn_signUp = findViewById<MaterialButton>(R.id.btn_signUp)


        txt_to_sign_in.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        btn_signUp.setOnClickListener {
            var txt_email = edt_email.text.toString()
            var txt_password = edt_password.text.toString()
            var txt_password_again = edt_password_repeat.text.toString()

            if (txt_email != "" && android.util.Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()){
                if (txt_password != "" && txt_password.length > 8 && txt_password_again != "" && txt_password_again == txt_password){
                    Toast.makeText(this, "Successful for now", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this, "Invalid password!", Toast.LENGTH_SHORT).show()
                    txt_password = ""
                    edt_password.setText(txt_password)
                    txt_password_again = ""
                    edt_password_repeat.setText(txt_password_again)
                }
            } else {
                Toast.makeText(this, "Invalid email!", Toast.LENGTH_SHORT).show()
                txt_email = ""
                edt_email.setText(txt_email)
            }
        }
    }
}