package com.ftbw.app.bestworld.adapter.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.COMPANY
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.PARTICULAR
import com.ftbw.app.bestworld.view.fragments.users.UsersRVTab

class UsersViewPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UsersRVTab(PARTICULAR)
            1 -> UsersRVTab(COMPANY)
            else -> UsersRVTab(PARTICULAR)
        }
    }
}