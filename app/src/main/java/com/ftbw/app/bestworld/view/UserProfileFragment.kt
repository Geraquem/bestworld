package com.ftbw.app.bestworld.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.databinding.FragmentEventsBinding

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

}