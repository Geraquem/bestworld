package com.ftbw.app.bestworld.view.create.createpost

import com.ftbw.app.bestworld.model.user.CreatorDTO

interface CreatePostView {
    fun creatorOfEvent(creator: CreatorDTO)
    fun postCreated()
    fun setErrorMessage()
    fun somethingWentWrong()
}