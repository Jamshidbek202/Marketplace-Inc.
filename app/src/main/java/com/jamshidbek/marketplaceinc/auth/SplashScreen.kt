package com.jamshidbek.marketplaceinc.auth

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
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
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val firestoreDatabase = FirebaseFirestore.getInstance()
        var model = UserModel()

        Handler().postDelayed({
            if (user == null) {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                if (isOnline(this)){
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
                } else {
                    Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 3000)
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}