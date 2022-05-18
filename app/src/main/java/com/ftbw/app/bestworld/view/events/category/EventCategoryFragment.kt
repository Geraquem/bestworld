package com.ftbw.app.bestworld.view.events.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.databinding.FragmentEventCategoryBinding
import com.ftbw.app.bestworld.view.ICommunication
import com.ftbw.app.bestworld.view.events.EventsFragment

class EventCategoryFragment(val listener: ICommunication) : Fragment() {

    private var _bdg: FragmentEventCategoryBinding? = null
    private val bdg get() = _bdg!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bdg = FragmentEventCategoryBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bdg.groupAllEvents.setOnClickListener { listener.openFragment(EventsFragment()) }
    }
}