package com.ftbw.app.bestworld.view.eventfile

import com.ftbw.app.bestworld.model.event.EventDTO

interface EventFileView {
    fun setEventData(event: EventDTO, userExists: Boolean, isUserSignedUp: Boolean)

    fun showEventImage(exists: Boolean, imageURL: String)
    fun setOtherInfo(view: Int, otherInfo: String)

    fun userSignedUpInEvent(assistants: Long, signedUp: Boolean)

    fun somethingWentWrong()
}