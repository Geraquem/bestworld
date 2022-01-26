package com.ftbw.app.bestworld.view.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.FragmentEventsBinding
import com.ftbw.app.bestworld.databinding.FragmentPostsBinding
import com.ftbw.app.bestworld.view.events.adapter.viewpager.EventsViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class PostsFragment : Fragment() {

    private var _bdg: FragmentPostsBinding? = null
    private val bdg get() = _bdg!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bdg = FragmentPostsBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}