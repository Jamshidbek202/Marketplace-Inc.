package com.jamshidbek.marketplaceinc.auth

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.jamshidbek.marketplaceinc.R

class UserDetailsActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val txt_email = intent.getStringExtra("email")
        val txt_password = intent.getStringExtra("password")
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
                Toast.makeText(this, "$txt_name, $txt_surname, $txt_phone, $txt_city, $txt_email, $txt_password", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Fill in all the information", Toast.LENGTH_SHORT).show()
            }
        }
    }
}