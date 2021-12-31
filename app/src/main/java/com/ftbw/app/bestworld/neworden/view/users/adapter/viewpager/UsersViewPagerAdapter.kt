package com.ftbw.app.bestworld.neworden.view.users.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.COMPANY
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.PARTICULAR
import com.ftbw.app.bestworld.neworden.view.users.tabs.UsersRVTab

class UsersViewPagerAdapter(
    val listener: UsersRVTab.IOpenUserProfileFromUsers,
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