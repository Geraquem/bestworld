package com.ftbw.app.bestworld.neworden.view.createevent

interface CreateEventView {
    fun creatorOfEvent(name: String)
    fun eventCreated()

    fun setErrorMessage(message: String)
    fun somethingWentWrong()
}