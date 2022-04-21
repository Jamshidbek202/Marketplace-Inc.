package com.jamshidbek.marketplaceinc.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.jamshidbek.marketplaceinc.MainActivity
import com.jamshidbek.marketplaceinc.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val main_logo = findViewById<ImageView>(R.id.img_logo)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        main_logo.startAnimation(animation)

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        Toast.makeText(this, ""+user.toString(), Toast.LENGTH_SHORT).show()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            if (user == null){
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val mDatabase = FirebaseDatabase.getInstance().getReference("Users")

                mDatabase.child(user.uid).child("name").get().addOnSuccessListener {
                    val name = it.value.toString()

                    if (name == null){
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("uid", user.uid)
                    startActivity(intent)
                    Toast.makeText(this@SplashScreen, "Logged in as: $name", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }, 3000)
    }
}