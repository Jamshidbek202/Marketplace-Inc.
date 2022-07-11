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
import com.jamshidbek.marketplaceinc.utils.docs.db.DBHelper
import com.jamshidbek.marketplaceinc.utils.models.UserModel

class SplashScreen : AppCompatActivity() {

    var model = UserModel()

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

        Handler().postDelayed({
            if (user == null) {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                if (isOnline(this)){
                    //get the document
                    firestoreDatabase.collection("Users").document(user.uid).get().addOnSuccessListener { document ->
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

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.putExtra("uid", user.uid)
                    startActivity(intent)
                    finish()
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