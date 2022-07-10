package com.jamshidbek.marketplaceinc.utils.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jamshidbek.marketplaceinc.mainfragments.*

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> OnSaleFragment()
            1 -> SearchFragment()
            2 -> AddToSaleFragment()
            3 -> CartFragment()
            4 -> ProfileFragment()
            else -> OnSaleFragment()
        }
    }

    override fun getCount(): Int {
        return 5
    }
}