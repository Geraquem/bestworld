package com.ftbw.app.bestworld.adapter.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.helper.EventHelper.Companion.DIVULGATION
import com.ftbw.app.bestworld.helper.EventHelper.Companion.ENVIRONMENTAL
import com.ftbw.app.bestworld.helper.EventHelper.Companion.FARMING
import com.ftbw.app.bestworld.helper.EventHelper.Companion.MOBILIZATION
import com.ftbw.app.bestworld.helper.EventHelper.Companion.OTHER
import com.ftbw.app.bestworld.helper.EventHelper.Companion.WORKSHOP
import com.ftbw.app.bestworld.view.fragments.events.EventsRVTab

class EventsViewPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EventsRVTab(ENVIRONMENTAL)
            1 -> EventsRVTab(DIVULGATION)
            2 -> EventsRVTab(FARMING)
            3 -> EventsRVTab(MOBILIZATION)
            4 -> EventsRVTab(WORKSHOP)
            5 -> EventsRVTab(OTHER)
            else -> EventsRVTab(OTHER)
        }
    }
}