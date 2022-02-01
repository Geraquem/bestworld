package com.ftbw.app.bestworld.view.userprofile.tabs.events

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.FragmentTabEventsUserProfileBinding
import com.ftbw.app.bestworld.view.userprofile.adapter.recyclerview.RViewUserProfileEventsAdapter
import com.ftbw.app.bestworld.view.userprofile.adapter.viewpager.UserProfileEventsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class UserProfileEventsTab(var userKey: String) : Fragment() {

    private var _bdg: FragmentTabEventsUserProfileBinding? = null
    private val bdg get() = _bdg!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bdg = FragmentTabEventsUserProfileBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = UserProfileEventsPagerAdapter(this, userKey)
        bdg.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(bdg.tabLayout, bdg.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_tab_created)
                }
                1 -> {
                    tab.setIcon(R.drawable.ic_event_assistant)
                }
            }
        }.attach()
    }
}