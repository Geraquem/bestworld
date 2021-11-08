package com.ftbw.app.bestworld.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.RowEventRecyclerBinding
import com.ftbw.app.bestworld.model.EventRecyclerDTO

class RViewEventsAdapter(private var eventList: List<EventRecyclerDTO>) :
    RecyclerView.Adapter<RViewEventsAdapter.EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        return EventHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_event_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemCount() = eventList.size

    class EventHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bdg = RowEventRecyclerBinding.bind(view)

        fun bind(event: EventRecyclerDTO) {
            bdg.name.text = event.name
        }
    }
}