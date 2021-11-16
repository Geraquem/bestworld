package com.ftbw.app.bestworld.adapter.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.view.fragments.events.*
import com.ftbw.app.bestworld.view.fragments.users.UserRVTab

class UsersViewPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserRVTab("particular")
            1 -> UserRVTab("company")
            else -> UserRVTab("particular")
        }
    }
}