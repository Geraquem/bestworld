package com.ftbw.app.bestworld.view.userprofile.tabs.events.tabs

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO

interface UserProfileEventsRVTabView {
    fun setAllEvents(events: List<EventRecyclerDTO>)
    fun setEvents(events: List<EventRecyclerDTO>)
    fun somethingWentWrong()
}