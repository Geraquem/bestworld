package com.ftbw.app.bestworld.view.users.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.helper.EventCommon.Companion.COMPANY
import com.ftbw.app.bestworld.helper.EventCommon.Companion.PARTICULAR
import com.ftbw.app.bestworld.view.ICommunication
import com.ftbw.app.bestworld.view.users.tabs.UsersRVTab

class UsersViewPagerAdapter(
    val listener: ICommunication,
    val fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UsersRVTab(listener, PARTICULAR)
            1 -> UsersRVTab(listener, COMPANY)
            else -> UsersRVTab(listener, PARTICULAR)
        }
    }
}