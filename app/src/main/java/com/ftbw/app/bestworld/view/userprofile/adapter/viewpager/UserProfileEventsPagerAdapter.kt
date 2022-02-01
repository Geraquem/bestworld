package com.ftbw.app.bestworld.view.userprofile.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.helper.EventCommon.Companion.ASSISTANT_EVENTS
import com.ftbw.app.bestworld.helper.EventCommon.Companion.CREATED_EVENTS
import com.ftbw.app.bestworld.view.userprofile.tabs.events.tabs.UserProfileEventsRVTab

class UserProfileEventsPagerAdapter (val fragment: Fragment, var userKey: String) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserProfileEventsRVTab(CREATED_EVENTS, userKey)
            1 -> UserProfileEventsRVTab(ASSISTANT_EVENTS, userKey)
            else -> UserProfileEventsRVTab(CREATED_EVENTS, userKey)
        }
    }
}