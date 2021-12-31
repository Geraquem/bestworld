package com.ftbw.app.bestworld.view.createevent

interface CreateEventView {
    fun creatorOfEvent(name: String)
    fun eventCreated()

    fun setErrorMessage(message: String)
    fun somethingWentWrong()
}