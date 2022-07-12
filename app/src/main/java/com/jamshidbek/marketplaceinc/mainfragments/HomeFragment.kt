package com.jamshidbek.marketplaceinc.mainfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.utils.adapters.ViewPagerAdapter

class HomeFragment : Fragment() {

    lateinit var onSaleFragment : OnSaleFragment
    lateinit var searchFragment : SearchFragment
    lateinit var addToSaleFragment : AddToSaleFragment
    lateinit var cartFragment : CartFragment
    lateinit var profileFragment : ProfileFragment
    lateinit var bottomNavigationView : BottomNavigationView
    lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        initialize(view)
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

        return view
    }

    private fun setUpViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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

    private fun initialize(view: View) {
        onSaleFragment = OnSaleFragment()
        searchFragment = SearchFragment()
        addToSaleFragment = AddToSaleFragment()
        cartFragment = CartFragment()
        profileFragment = ProfileFragment()
        bottomNavigationView = view.findViewById(R.id.bottom_navigation)
        viewPager = view.findViewById(R.id.main_view_pager)
    }
}