package com.ftbw.app.bestworld.view.fragments.events

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftbw.app.bestworld.adapter.recyclerview.RViewEventsAdapter
import com.ftbw.app.bestworld.databinding.FragmentTabEventBinding
import com.ftbw.app.bestworld.helper.EventHelper.Companion.getLabelInSpanish
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.ftbw.app.bestworld.viewmodel.EventsViewModel

class EventsRVTab(var label: String) : Fragment() {
    private var _bdg: FragmentTabEventBinding? = null
    private val bdg get() = _bdg!!

    lateinit var getContext: Context

    private lateinit var viewModel: EventsViewModel

    private lateinit var adapter: RViewEventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bdg = FragmentTabEventBinding.inflate(inflater, container, false)
        return bdg.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        bdg.loading.root.visibility = View.VISIBLE
        bdg.suchEmpty.root.visibility = View.GONE

        val labelText = getLabelInSpanish(getContext, label)
        bdg.eventTabTitle.text = labelText

        viewModel.getEvents(label)
        viewModel.listEventRecycler.observe(viewLifecycleOwner, {
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
        bdg.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RViewEventsAdapter(requireContext(), list)
        bdg.recyclerView.adapter = adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getContext = context
    }
}