package com.ftbw.app.bestworld.view.create.createpost

interface CreatePostView {
    fun creatorOfEvent(name: String)
    fun postCreated()

    fun setErrorMessage()
    fun somethingWentWrong()
}