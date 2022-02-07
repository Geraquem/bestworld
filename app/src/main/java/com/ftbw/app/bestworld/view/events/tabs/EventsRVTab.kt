package com.ftbw.app.bestworld.view.events.tabs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.view.events.adapter.recyclerview.RViewEventsAdapter
import com.ftbw.app.bestworld.databinding.FragmentTabEventBinding
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.ftbw.app.bestworld.helper.EventCommon
import com.ftbw.app.bestworld.helper.EventCommon.Companion.getLabelInSpanish
import com.ftbw.app.bestworld.view.events.EventsPresenter
import com.ftbw.app.bestworld.view.events.EventsView

class EventsRVTab(var label: String) : Fragment(), EventsView {
    private var _bdg: FragmentTabEventBinding? = null
    private val bdg get() = _bdg!!

    lateinit var mContext: Context

    private val presenter by lazy { EventsPresenter(this) }

    private lateinit var adapter: RViewEventsAdapter

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

        bdg.loading.root.visibility = View.VISIBLE

        val labelText = getLabelInSpanish(mContext, label)
        bdg.eventTabTitle.text = labelText

        presenter.getEventsByLabel(label)
    }

    override fun showEvents(events: List<EventRecyclerDTO>) {
        bdg.loading.root.visibility = View.GONE
        if (events.isEmpty()) {
            bdg.suchEmpty.root.visibility = View.VISIBLE
        } else {
            bdg.suchEmpty.root.visibility = View.GONE
            bdg.recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = RViewEventsAdapter(
                { EventCommon.goToEventFile(mContext, it) }, mContext, events
            )
            bdg.recyclerView.adapter = adapter
        }
    }

    override fun somethingWentWrong() {
        Toast.makeText(mContext, getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}