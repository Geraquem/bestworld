package com.ftbw.app.bestworld.view.userprofile.tabs.events.tabs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.FragmentTabUserProfileBinding
import com.ftbw.app.bestworld.helper.EventCommon.Companion.ALL_EVENTS
import com.ftbw.app.bestworld.helper.EventCommon.Companion.ASSISTANT_EVENTS
import com.ftbw.app.bestworld.helper.EventCommon.Companion.CREATED_EVENTS
import com.ftbw.app.bestworld.helper.EventCommon.Companion.getLabelInEnglish
import com.ftbw.app.bestworld.helper.EventCommon.Companion.goToEventFile
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.ftbw.app.bestworld.view.userprofile.adapter.recyclerview.RViewUserProfileAllEventsAdapter
import com.ftbw.app.bestworld.view.userprofile.adapter.recyclerview.RViewUserProfileEventsAdapter

class UserProfileEventsRVTab(var type: String, var userKey: String) : Fragment(),
    UserProfileEventsRVTabView, AdapterView.OnItemSelectedListener {

    private var _bdg: FragmentTabUserProfileBinding? = null
    private val bdg get() = _bdg!!

    lateinit var mContext: Context

    private val presenter by lazy { UserProfileEventsRVTabPresenter(this) }

    private lateinit var eventsAdapter: RViewUserProfileEventsAdapter
    private lateinit var allEventsAdapter: RViewUserProfileAllEventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bdg = FragmentTabUserProfileBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bdg.spinner.adapter = presenter.spinnerAdapter(mContext)
        bdg.spinner.onItemSelectedListener = this

        bdg.title.text = when (type) {
            CREATED_EVENTS -> getString(R.string.createdEvents)
            ASSISTANT_EVENTS -> getString(R.string.assistedEvents)
            else -> getString(R.string.somethingWentWrong)
        }
    }

    override fun setAllEvents(events: List<EventRecyclerDTO>) {
        bdg.loading.root.visibility = View.GONE
        if (events.isEmpty()) {
            bdg.suchEmpty.root.visibility = View.VISIBLE
        } else {
            bdg.suchEmpty.root.visibility = View.GONE
            bdg.recyclerView.layoutManager = LinearLayoutManager(mContext)
            allEventsAdapter = RViewUserProfileAllEventsAdapter(
                { goToEventFile(mContext, it) }, mContext, events
            )
            bdg.recyclerView.adapter = allEventsAdapter
            bdg.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun setEvents(events: List<EventRecyclerDTO>) {
        bdg.loading.root.visibility = View.GONE
        if (events.isEmpty()) {
            bdg.suchEmpty.root.visibility = View.VISIBLE
        } else {
            bdg.suchEmpty.root.visibility = View.GONE
            bdg.recyclerView.layoutManager = LinearLayoutManager(mContext)
            eventsAdapter = RViewUserProfileEventsAdapter(
                { goToEventFile(mContext, it) }, mContext, events
            )
            bdg.recyclerView.adapter = eventsAdapter
            bdg.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        bdg.loading.root.visibility = View.VISIBLE
        bdg.recyclerView.visibility = View.GONE
        val eventLabel = getLabelInEnglish(mContext, p0!!.getItemAtPosition(p2).toString())
        if (eventLabel != ALL_EVENTS) {
            selectTab(eventLabel)
        } else {
            selectTab(ALL_EVENTS)
        }
    }

    private fun selectTab(eventLabel: String) {
        if (type == CREATED_EVENTS) {
            presenter.getEventsRelatedWithUser(CREATED_EVENTS, userKey, eventLabel)
        } else if (type == ASSISTANT_EVENTS) {
            presenter.getEventsRelatedWithUser(ASSISTANT_EVENTS, userKey, eventLabel)
        }
    }

    override fun somethingWentWrong() {
        Toast.makeText(mContext, getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}