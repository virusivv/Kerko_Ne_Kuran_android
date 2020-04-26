package com.kerko.ne.kuran.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.ArrayList

/**
 * Created by Ardian Ahmeti on 03/07/2020
 **/
class MainPagerAdapter(fm: FragmentManager,val fragmentList: ArrayList<Fragment>) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment){
        fragmentList.add(fragment)
    }

}