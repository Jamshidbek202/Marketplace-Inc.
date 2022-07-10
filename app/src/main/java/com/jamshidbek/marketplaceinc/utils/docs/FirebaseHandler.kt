package com.jamshidbek.marketplaceinc.utils.docs

import android.app.Application
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHandler : Application(){

    override fun onCreate() {
        super.onCreate()

        Firebase.database.setPersistenceEnabled(true)
        //if the user loses connection and enters the app again with no connection, this thing works
    }
}