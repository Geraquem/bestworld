package com.ftbw.app.bestworld.view.events

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EventsPresenter(private val view: EventsView) : EventsRepository.IEvents, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val repository by lazy { EventsRepository(this) }

    fun getAllEvents() {
        launch(Dispatchers.IO) {
            repository.getAllEvents()
        }
    }

    fun getEventsByLabel(label: String) {
        launch(Dispatchers.IO) {
            repository.getEventsByLabel(label)
        }
    }

    override fun showEvents(events: List<EventRecyclerDTO>) {
        launch {
            view.showEvents(events)
        }
    }

    override fun somethingWentWrong() {
        launch {
            view.somethingWentWrong()
        }
    }
}