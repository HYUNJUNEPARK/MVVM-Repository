package com.example.viewpager2_bottomnavigation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    var fragmentList = listOf<Fragment>()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return  when(position) {
            0 -> fragmentList[0]
            1 -> fragmentList[1]
            2 -> fragmentList[2]
            3 -> fragmentList[3]
            4 -> fragmentList[4]
            else -> fragmentList[0]
        }
    }
}
