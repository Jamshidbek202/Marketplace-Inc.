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

    lateinit var onSaleFragment : OnSaleFragment
    lateinit var searchFragment : SearchFragment
    lateinit var addToSaleFragment : AddToSaleFragment
    lateinit var cartFragment : CartFragment
    lateinit var profileFragment : ProfileFragment
    lateinit var bottomNavigationView : BottomNavigationView
    lateinit var viewPager: ViewPager
    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UserUID.user_uid = uid
        initialize()
        setUpViewPager()

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.onSaleFragment -> viewPager.currentItem = 0
                R.id.searchFragment -> viewPager.currentItem = 1
                R.id.addToSaleFragment -> viewPager.currentItem = 2
                R.id.cartFragment -> viewPager.currentItem = 3
                R.id.profileFragment -> viewPager.currentItem = 4
            }
            true
        })
    }

//    private fun setUpData() {
//
//        val mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid)
//
//        mDatabase.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var model = UserModel()
//                model = snapshot.getValue(UserModel::class.java)!!
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(applicationContext, "" + error.message, Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })
//    }

    private fun setUpViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNavigationView.getMenu().findItem(R.id.onSaleFragment).setChecked(true)
                    1 -> bottomNavigationView.getMenu().findItem(R.id.searchFragment).setChecked(true)
                    2 -> bottomNavigationView.getMenu().findItem(R.id.addToSaleFragment).setChecked(true)
                    3 -> bottomNavigationView.getMenu().findItem(R.id.cartFragment).setChecked(true)
                    4 -> bottomNavigationView.getMenu().findItem(R.id.profileFragment).setChecked(true)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        viewPager.adapter = viewPagerAdapter
    }

    private fun initialize() {
        onSaleFragment = OnSaleFragment()
        searchFragment = SearchFragment()
        addToSaleFragment = AddToSaleFragment()
        cartFragment = CartFragment()
        profileFragment = ProfileFragment()
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        viewPager = findViewById(R.id.main_view_pager)
    }
}