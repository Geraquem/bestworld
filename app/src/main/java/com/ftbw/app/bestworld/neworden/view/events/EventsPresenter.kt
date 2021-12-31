package com.ftbw.app.bestworld.neworden.view.events

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO

class EventsPresenter(private val view: EventsView) : EventsRepository.IEvents{

    private val repository by lazy { EventsRepository(this) }

    fun getAllEvents() {
        repository.getAllEvents()
    }

    fun getEventsByLabel(label: String) {
        repository.getEventsByLabel(label)
    }

    override fun showEvents(events: List<EventRecyclerDTO>) {
        view.showEvents(events)
    }

    override fun somethingWentWrong() {
        view.somethingWentWrong()
    }
}