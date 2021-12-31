package com.ftbw.app.bestworld.neworden.view.events

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO

interface EventsView {
    fun showEvents(events: List<EventRecyclerDTO>)
    fun somethingWentWrong()
}