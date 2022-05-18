package com.ftbw.app.bestworld.view.events.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.helper.EventCommon.Companion.DIVULGATION
import com.ftbw.app.bestworld.helper.EventCommon.Companion.ENVIRONMENTAL
import com.ftbw.app.bestworld.helper.EventCommon.Companion.FARMING
import com.ftbw.app.bestworld.helper.EventCommon.Companion.MOBILIZATION
import com.ftbw.app.bestworld.helper.EventCommon.Companion.OTHER
import com.ftbw.app.bestworld.helper.EventCommon.Companion.SHARING_CAR
import com.ftbw.app.bestworld.helper.EventCommon.Companion.WORKSHOP
import com.ftbw.app.bestworld.view.events.tabs.AllEventsRVTab
import com.ftbw.app.bestworld.view.events.tabs.EventsRVTab

class EventsViewPagerAdapter(val fragment: Fragment, val isMyNetwork: Boolean) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 8

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllEventsRVTab(isMyNetwork)
            1 -> EventsRVTab(ENVIRONMENTAL, isMyNetwork)
            2 -> EventsRVTab(DIVULGATION, isMyNetwork)
            3 -> EventsRVTab(FARMING, isMyNetwork)
            4 -> EventsRVTab(MOBILIZATION, isMyNetwork)
            5 -> EventsRVTab(WORKSHOP, isMyNetwork)
            6 -> EventsRVTab(SHARING_CAR, isMyNetwork)
            7 -> EventsRVTab(OTHER, isMyNetwork)
            else -> EventsRVTab(OTHER, isMyNetwork)
        }
    }
}