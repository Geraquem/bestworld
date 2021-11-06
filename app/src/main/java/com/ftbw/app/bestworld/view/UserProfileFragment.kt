package com.ftbw.app.bestworld.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.adapter.ViewPagerAdapter
import com.ftbw.app.bestworld.databinding.FragmentEventsBinding
import com.google.android.material.tabs.TabLayoutMediator

class UserProfileFragment : Fragment() {

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

    }
}