package com.ftbw.app.bestworld.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.adapter.ViewPagerAdapter
import com.ftbw.app.bestworld.databinding.FragmentEventsBinding
import com.google.android.material.tabs.TabLayoutMediator

class EventsFragment : Fragment() {

    /*

    Credits:    Icons by svgrepo.com

     */

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

        val viewPagerAdapter = ViewPagerAdapter(this)
        bdg.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(bdg.tabLayout, bdg.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_tab_enviromental)
                    //tab.setText(R.string.EnviromentalTitle)
                }
                1 -> {
                    tab.setIcon(R.drawable.ic_tab_divulgation)
                    //tab.setText(R.string.DivulgationTitle)
                }
                2 -> {
                    tab.setIcon(R.drawable.ic_tab_workshop)
                    //tab.setText(R.string.WorkshopTitle)
                }
                3 -> {
                    tab.setIcon(R.drawable.ic_tab_mobilization)
                    //tab.setText(R.string.MobilizationTitle)
                }
                4 -> {
                    tab.setIcon(R.drawable.ic_tab_farming)
                    //tab.setText(R.string.FarmingTitle)
                }
                5 -> {
                    tab.setIcon(R.drawable.ic_tab_other)
                    //tab.setText(R.string.OtherTitle)
                }
            }
        }.attach()
    }
}