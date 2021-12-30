package com.ftbw.app.bestworld.neworden.view.events

class EventsPresenter(private val view: EventsView) : EventsRepository.IEvents{

    private val repository by lazy { EventsRepository(this) }


}