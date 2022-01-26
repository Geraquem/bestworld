package com.ftbw.app.bestworld.view.posts

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PostsPresenter(private val view: PostsView) : PostsRepository.IPosts, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val repository by lazy { PostsRepository(this) }

//    fun getAllEvents() {
//        launch(Dispatchers.IO) {
//            repository.getAllEvents()
//        }
//    }

    override fun somethingWentWrong() {
        launch {
            view.somethingWentWrong()
        }
    }
}