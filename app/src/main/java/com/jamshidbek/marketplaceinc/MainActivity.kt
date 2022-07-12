package com.jamshidbek.marketplaceinc

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.jamshidbek.marketplaceinc.mainfragments.*
import com.jamshidbek.marketplaceinc.utils.adapters.ViewPagerAdapter
import com.jamshidbek.marketplaceinc.utils.docs.UserUID

class MainActivity : AppCompatActivity() {

    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UserUID.user_uid = uid

    }
}