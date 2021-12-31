package com.ftbw.app.bestworld.view.events.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.helper.Common.Companion.DIVULGATION
import com.ftbw.app.bestworld.helper.Common.Companion.ENVIRONMENTAL
import com.ftbw.app.bestworld.helper.Common.Companion.FARMING
import com.ftbw.app.bestworld.helper.Common.Companion.MOBILIZATION
import com.ftbw.app.bestworld.helper.Common.Companion.OTHER
import com.ftbw.app.bestworld.helper.Common.Companion.WORKSHOP
import com.ftbw.app.bestworld.view.events.tabs.AllEventsRVTab
import com.ftbw.app.bestworld.view.events.tabs.EventsRVTab

class EventsViewPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllEventsRVTab()
            1 -> EventsRVTab(ENVIRONMENTAL)
            2 -> EventsRVTab(DIVULGATION)
            3 -> EventsRVTab(FARMING)
            4 -> EventsRVTab(MOBILIZATION)
            5 -> EventsRVTab(WORKSHOP)
            6 -> EventsRVTab(OTHER)
            else -> EventsRVTab(OTHER)
        }
    }
}