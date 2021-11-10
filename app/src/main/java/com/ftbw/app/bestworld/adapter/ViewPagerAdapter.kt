package com.ftbw.app.bestworld.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ftbw.app.bestworld.view.fragments.events.*

class ViewPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EnvironmentalTab()
            1 -> DivulgationTab()
            2 -> WorkshopTab()
            3 -> MobilizationTab()
            4 -> FarmingTab()
            5 -> OtherTab()
            else -> EnvironmentalTab()
        }
    }
}