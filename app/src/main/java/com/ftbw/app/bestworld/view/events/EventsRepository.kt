package com.ftbw.app.bestworld.view.events

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EventsRepository(private val listener: IEvents) {

    fun getAllEvents() {
        val labelList: MutableList<String> = mutableListOf()
        Firebase.database.reference.child("events").get().addOnSuccessListener {
            for (label in it.children) {
                labelList.add(label.key.toString())
            }
            getAllEventsGivenLabels(labelList)

        }.addOnFailureListener {
            listener.somethingWentWrong()
        }
    }

    private fun getAllEventsGivenLabels(labels: MutableList<String>) {
        val events: MutableList<EventRecyclerDTO> = mutableListOf()
        if (labels.isEmpty()) {
            listener.showEvents(events)
        } else {
            for (eventLabel in labels) {
                Firebase.database.reference.child("events").child(eventLabel)
                    .get().addOnSuccessListener {
                        for (event in it.children) {
                            events.add(event.getValue(EventRecyclerDTO::class.java)!!)
                        }
                        listener.showEvents(events)
                    }.addOnFailureListener {
                        listener.somethingWentWrong()
                    }
            }
        }
    }

    fun getEventsByLabel(label: String) {
        val events: MutableList<EventRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("events").child(label)
            .get().addOnSuccessListener {
                events.clear()
                for (event in it.children) {
                    events.add(event.getValue(EventRecyclerDTO::class.java)!!)
                }
                listener.showEvents(events)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    interface IEvents {
        fun showEvents(events: List<EventRecyclerDTO>)
        fun somethingWentWrong()
    }
}