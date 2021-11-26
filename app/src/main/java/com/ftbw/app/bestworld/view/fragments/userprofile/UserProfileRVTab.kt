package com.ftbw.app.bestworld.view.fragments.userprofile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.adapter.recyclerview.RViewEventsAdapter
import com.ftbw.app.bestworld.databinding.FragmentTabUserProfileBinding
import com.ftbw.app.bestworld.helper.EventHelper
import com.ftbw.app.bestworld.helper.EventHelper.Companion.CHOOSE_CATEGORY
import com.ftbw.app.bestworld.helper.UserHelper.Companion.ASSISTANT_EVENTS
import com.ftbw.app.bestworld.helper.UserHelper.Companion.CREATED_EVENTS
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.ftbw.app.bestworld.viewmodel.EventsViewModel

class UserProfileRVTab(var type: String, var userKey: String) : Fragment(),
    AdapterView.OnItemSelectedListener {
    private var _bdg: FragmentTabUserProfileBinding? = null
    private val bdg get() = _bdg!!

    lateinit var getContext: Context

    private lateinit var eventsViewModel: EventsViewModel

    private lateinit var adapter: RViewEventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bdg = FragmentTabUserProfileBinding.inflate(inflater, container, false)
        return bdg.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        ArrayAdapter.createFromResource(
            getContext,
            R.array.event_labels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)
            bdg.spinner.adapter = adapter
        }
        bdg.spinner.onItemSelectedListener = this

        bdg.loading.root.visibility = View.GONE
        bdg.suchEmpty.root.visibility = View.GONE

        if (type == CREATED_EVENTS) {
            bdg.title.text = getString(R.string.createdEvents)
        } else if (type == ASSISTANT_EVENTS) {
            bdg.title.text = getString(R.string.assistedEvents)
        } else {
            Toast.makeText(getContext, getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT)
                .show()
        }

        eventsViewModel.listOfEvents.observe(viewLifecycleOwner, {
            bdg.loading.root.visibility = View.GONE
            if (it.isEmpty()) {
                bdg.suchEmpty.root.visibility = View.VISIBLE
            } else {
                initRecyclerView(it)
                adapter.notifyDataSetChanged()
                bdg.suchEmpty.root.visibility = View.GONE
            }
        })
    }

    private fun initRecyclerView(list: List<EventRecyclerDTO>) {
        bdg.recyclerView.layoutManager = LinearLayoutManager(getContext)
        adapter = RViewEventsAdapter(requireContext(), list)
        bdg.recyclerView.adapter = adapter
        bdg.recyclerView.visibility = View.VISIBLE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getContext = context
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val eventLabel =
            EventHelper.getLabelInEnglish(getContext, p0!!.getItemAtPosition(p2).toString())
        if (eventLabel != CHOOSE_CATEGORY) {
            bdg.loading.root.visibility = View.VISIBLE
            bdg.recyclerView.visibility = View.GONE
            selectTab(eventLabel)

        } else {
            bdg.recyclerView.visibility = View.GONE
            bdg.loading.root.visibility = View.GONE
            bdg.suchEmpty.root.visibility = View.GONE
        }
    }

    private fun selectTab(eventLabel: String) {
        if (type == CREATED_EVENTS) {
            eventsViewModel.getEventsRelatedWithUser(CREATED_EVENTS, userKey, eventLabel)
        } else if (type == ASSISTANT_EVENTS) {
            eventsViewModel.getEventsRelatedWithUser(ASSISTANT_EVENTS, userKey, eventLabel)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}