package com.ftbw.app.bestworld.view.create.createevent

interface CreateEventView {
    fun creatorOfEvent(name: String)
    fun eventCreated()

    fun setErrorMessage(message: String)
    fun somethingWentWrong()
}