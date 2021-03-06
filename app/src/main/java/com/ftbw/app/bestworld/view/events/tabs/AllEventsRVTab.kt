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
import com.ftbw.app.bestworld.view.events.adapter.recyclerview.RViewAllEventsAdapter
import com.ftbw.app.bestworld.databinding.FragmentTabEventBinding
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.ftbw.app.bestworld.helper.EventCommon.Companion.goToEventFile
import com.ftbw.app.bestworld.view.events.EventsPresenter
import com.ftbw.app.bestworld.view.events.EventsView

class AllEventsRVTab(val isMyNetwork: Boolean) : Fragment(), EventsView {
    private var _bdg: FragmentTabEventBinding? = null
    private val bdg get() = _bdg!!

    lateinit var mContext: Context

    private val presenter by lazy { EventsPresenter(this) }

    private lateinit var adapter: RViewAllEventsAdapter

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

        bdg.eventTabTitle.text = getString(R.string.allEvents)

        if(isMyNetwork){
            //presenter.getAllEventsOnMyNetwork()
        }else{
            presenter.getAllEvents()
        }
    }

    override fun showEvents(events: List<EventRecyclerDTO>) {
        bdg.loading.root.visibility = View.GONE
        if (events.isEmpty()) {
            bdg.suchEmpty.root.visibility = View.VISIBLE
        } else {
            bdg.suchEmpty.root.visibility = View.GONE
            bdg.recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = RViewAllEventsAdapter(
                { goToEventFile(mContext, it) }, mContext, events
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