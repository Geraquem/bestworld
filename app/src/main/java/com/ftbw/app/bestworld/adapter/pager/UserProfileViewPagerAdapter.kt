package com.ftbw.app.bestworld.adapter.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.helper.UserHelper.Companion.VIEW_ASSISTANT_EVENTS
import com.ftbw.app.bestworld.helper.UserHelper.Companion.VIEW_CREATED_EVENTS
import com.ftbw.app.bestworld.view.fragments.userprofile.UserProfileRVTab

class UserProfileViewPagerAdapter(val fragment: Fragment, var userKey: String) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserProfileRVTab(VIEW_CREATED_EVENTS, userKey)
            1 -> UserProfileRVTab(VIEW_ASSISTANT_EVENTS, userKey)
            else -> UserProfileRVTab(VIEW_CREATED_EVENTS, userKey)
        }
    }
}