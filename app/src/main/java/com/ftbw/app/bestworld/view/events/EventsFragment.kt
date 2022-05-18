package com.ftbw.app.bestworld.view.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.FragmentEventsBinding
import com.ftbw.app.bestworld.view.events.adapter.viewpager.EventsViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class EventsFragment(val pos: Int, val isMyNetwork: Boolean) : Fragment() {
    private var _bdg: FragmentEventsBinding? = null
    private val bdg get() = _bdg!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bdg = FragmentEventsBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = EventsViewPagerAdapter(this, isMyNetwork)
        bdg.viewPager.adapter = viewPagerAdapter

        bdg.viewPager.setCurrentItem(pos, false)

        TabLayoutMediator(bdg.tabLayout, bdg.viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_tab_all_events)
                1 -> tab.setIcon(R.drawable.ic_tab_enviromental)
                2 -> tab.setIcon(R.drawable.ic_tab_divulgation)
                3 -> tab.setIcon(R.drawable.ic_tab_workshop)
                4 -> tab.setIcon(R.drawable.ic_tab_mobilization)
                5 -> tab.setIcon(R.drawable.ic_tab_farming)
                6 -> tab.setIcon(R.drawable.ic_tab_sharing_car)
                7 -> tab.setIcon(R.drawable.ic_tab_other)
            }
        }.attach()
    }
}