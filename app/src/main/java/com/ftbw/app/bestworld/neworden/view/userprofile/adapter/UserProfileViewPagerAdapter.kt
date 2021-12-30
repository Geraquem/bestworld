package com.ftbw.app.bestworld.neworden.view.userprofile.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.ASSISTANT_EVENTS
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.CREATED_EVENTS
import com.ftbw.app.bestworld.neworden.view.userprofile.tabs.UserProfileRVTab

class UserProfileViewPagerAdapter(val fragment: Fragment, var userKey: String) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserProfileRVTab(CREATED_EVENTS, userKey)
            1 -> UserProfileRVTab(ASSISTANT_EVENTS, userKey)
            else -> UserProfileRVTab(CREATED_EVENTS, userKey)
        }
    }
}