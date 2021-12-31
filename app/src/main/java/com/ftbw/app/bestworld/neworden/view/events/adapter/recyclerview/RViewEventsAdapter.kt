package com.ftbw.app.bestworld.neworden.view.events.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.RowEventRecyclerBinding
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.setEventImage

class RViewEventsAdapter(
    val onClick: (event: EventRecyclerDTO) -> Unit,
    var context: Context,
    private var eventsList: List<EventRecyclerDTO>
) :
    RecyclerView.Adapter<RViewEventsAdapter.EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        return EventHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_event_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(context, eventsList[position])
        holder.bdg.row.setOnClickListener {
            onClick(eventsList[position])
        }
    }

    override fun getItemCount() = eventsList.size

    class EventHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bdg = RowEventRecyclerBinding.bind(view)

        fun bind(context: Context, event: EventRecyclerDTO) {
            setEventImage(context, event.imageURL, bdg.imageURL)
            bdg.title.text = event.title
            bdg.creator.text = event.creatorName
            bdg.address.text = event.address
        }
    }
}