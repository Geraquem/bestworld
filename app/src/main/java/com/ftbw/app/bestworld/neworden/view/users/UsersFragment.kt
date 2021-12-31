package com.ftbw.app.bestworld.neworden.view.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.neworden.view.users.adapter.viewpager.UsersViewPagerAdapter
import com.ftbw.app.bestworld.databinding.FragmentUsersBinding
import com.google.android.material.tabs.TabLayoutMediator

class UsersFragment : Fragment() {

    private var _bdg: FragmentUsersBinding? = null
    private val bdg get() = _bdg!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bdg = FragmentUsersBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = UsersViewPagerAdapter(this)
        bdg.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(bdg.tabLayout, bdg.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_particular)
                    tab.setText(R.string.usersParticulars)
                }
                1 -> {
                    tab.setIcon(R.drawable.ic_company)
                    tab.setText(R.string.usersCompanies)
                }
            }
        }.attach()
    }
}