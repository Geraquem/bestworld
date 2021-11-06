package com.ftbw.app.bestworld.view.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.FragmentTabEventBinding

class FarmingTab : Fragment() {
    private var _bdg: FragmentTabEventBinding? = null
    private val bdg get() = _bdg!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bdg = FragmentTabEventBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bdg.eventTabTitle.text = getString(R.string.FarmingTitleTab)
    }
}