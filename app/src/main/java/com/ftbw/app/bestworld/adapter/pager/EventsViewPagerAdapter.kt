package com.ftbw.app.bestworld.adapter.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.helper.EventHelper.Companion.ALLEVENTS
import com.ftbw.app.bestworld.helper.EventHelper.Companion.DIVULGATION
import com.ftbw.app.bestworld.helper.EventHelper.Companion.ENVIRONMENTAL
import com.ftbw.app.bestworld.helper.EventHelper.Companion.FARMING
import com.ftbw.app.bestworld.helper.EventHelper.Companion.MOBILIZATION
import com.ftbw.app.bestworld.helper.EventHelper.Companion.OTHER
import com.ftbw.app.bestworld.helper.EventHelper.Companion.WORKSHOP
import com.ftbw.app.bestworld.view.fragments.events.AllEventsRVTab
import com.ftbw.app.bestworld.view.fragments.events.EventsRVTab

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