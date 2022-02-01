package com.ftbw.app.bestworld.view.userprofile.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.view.userprofile.tabs.events.UserProfileEventsTab
import com.ftbw.app.bestworld.view.userprofile.tabs.posts.UserProfilePostsTab

class UserProfileMainPagerAdapter(val fragment: Fragment, var userKey: String) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserProfilePostsTab(userKey)
            1 -> UserProfileEventsTab(userKey)
            else -> UserProfilePostsTab(userKey)
        }
    }
}