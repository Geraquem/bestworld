package com.ftbw.app.bestworld.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ftbw.app.bestworld.EventFile
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.RowEventRecyclerBinding
import com.ftbw.app.bestworld.model.EventRecyclerDTO

class RViewEventsAdapter(var context: Context, private var eventList: List<EventRecyclerDTO>) :
    RecyclerView.Adapter<RViewEventsAdapter.EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        return EventHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_event_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(context, eventList[position])
    }

    override fun getItemCount() = eventList.size

    class EventHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bdg = RowEventRecyclerBinding.bind(view)

        fun bind(context: Context, event: EventRecyclerDTO) {
            //set imageURL
            bdg.name.text = event.name
            bdg.creator.text = event.creator
            bdg.address.text = event.address

            bdg.row.setOnClickListener {
                context.startActivity(
                    Intent(
                        context,
                        EventFile::class.java
                    ).apply {
                        putExtra("key", event.key)
                        putExtra("label", event.label)
                    })
            }
        }
    }
}