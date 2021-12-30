package com.ftbw.app.bestworld.neworden.view.userprofile.tabs

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO

interface UserProfileRVTabView {
    fun setEvents(events: List<EventRecyclerDTO>)
    fun somethingWentWrong()
}