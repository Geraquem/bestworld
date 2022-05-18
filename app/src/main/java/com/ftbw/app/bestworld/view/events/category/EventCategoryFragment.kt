package com.ftbw.app.bestworld.view.events.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.FragmentEventCategoryBinding
import com.ftbw.app.bestworld.view.ICommunication
import com.ftbw.app.bestworld.view.events.EventsFragment

class EventCategoryFragment(val listener: ICommunication, val isMyNetwork: Boolean) : Fragment() {

    private var _bdg: FragmentEventCategoryBinding? = null
    private val bdg get() = _bdg!!

    private lateinit var mContext: Context

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
        setBackgroundColors()
        setClickListeners()
    }

    private fun setClickListeners() {
        bdg.groupAllEvents.setOnClickListener { openEventsFragment(0) }
        bdg.groupEnvironmental.setOnClickListener { openEventsFragment(1) }
        bdg.groupDivulgation.setOnClickListener { openEventsFragment(2) }
        bdg.groupWorkshop.setOnClickListener { openEventsFragment(3) }
        bdg.groupMobilization.setOnClickListener { openEventsFragment(4) }
        bdg.groupFarming.setOnClickListener { openEventsFragment(5) }
        bdg.groupSharingCar.setOnClickListener { openEventsFragment(6) }
        bdg.groupOther.setOnClickListener { openEventsFragment(7) }
    }

    private fun openEventsFragment(pos: Int) {
        listener.openFragment(EventsFragment(pos, isMyNetwork))
    }

    private fun setBackgroundColors() {
        bdg.groupAllEvents.background.setTint(getColor(mContext, R.color.AllEvents))
        bdg.groupEnvironmental.background.setTint(getColor(mContext, R.color.Enviromental))
        bdg.groupDivulgation.background.setTint(getColor(mContext, R.color.Divulgation))
        bdg.groupWorkshop.background.setTint(getColor(mContext, R.color.Workshop))
        bdg.groupMobilization.background.setTint(getColor(mContext, R.color.Mobilization))
        bdg.groupFarming.background.setTint(getColor(mContext, R.color.Farming))
        bdg.groupSharingCar.background.setTint(getColor(mContext, R.color.SharingCar))
        bdg.groupOther.background.setTint(getColor(mContext, R.color.Other))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}