package com.jamshidbek.marketplaceinc.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.jamshidbek.marketplaceinc.MainActivity
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.utils.docs.UserUID
import com.jamshidbek.marketplaceinc.utils.models.UserModel

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val main_logo = findViewById<ImageView>(R.id.img_logo)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        main_logo.startAnimation(animation)

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val firestoreDatabase = FirebaseFirestore.getInstance()
        var model = UserModel()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            if (user == null) {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(user.uid)

                mDatabase.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        model = snapshot.getValue(UserModel::class.java)!!

                        firestoreDatabase.collection("Users").document(user.uid).set(model, SetOptions.merge())
                        UserUID.user_uid = model.uid

                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("uid", user.uid)
                        startActivity(intent)
                        Toast.makeText(this@SplashScreen, "Logged in as: ${model.name}", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(applicationContext, "" + error.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
        }, 3000)
    }
}