package com.ftbw.app.bestworld.adapter.recyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.RowAllEventsRecyclerBinding
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.ftbw.app.bestworld.neworden.view.eventfile.EventFileActivity

class RViewAllEventsAdapter(var context: Context, private var eventsList: List<EventRecyclerDTO>) :
    RecyclerView.Adapter<RViewAllEventsAdapter.EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        return EventHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_all_events_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(context, eventsList[position])
    }

    override fun getItemCount() = eventsList.size

    class EventHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bdg = RowAllEventsRecyclerBinding.bind(view)

        fun bind(context: Context, event: EventRecyclerDTO) {
//            bdg.label.text = getLabelInSpanish(context, event.label)
//            setLabelBackgroundColor(context, bdg.label.background, event.label)
//            setImageEvent(context, event.imageURL, bdg.imageURL)
            bdg.title.text = event.title
            bdg.creator.text = event.creatorName
            bdg.address.text = event.address

            bdg.row.setOnClickListener {
                context.startActivity(
                    Intent(
                        context,
                        EventFileActivity::class.java
                    ).apply {
                        putExtra("key", event.key)
                        putExtra("label", event.label)
                    })
            }
        }
    }
}