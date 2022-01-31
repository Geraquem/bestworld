package com.ftbw.app.bestworld.view.posts

import com.ftbw.app.bestworld.model.post.PostDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PostsPresenter(private val view: PostsView) : PostsRepository.IPosts, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val repository by lazy { PostsRepository(this) }

    fun getPosts(userId: String) {
        launch(Dispatchers.IO) { repository.getAllPosts(userId) }
    }

    override fun showPosts(posts: List<PostDTO>) {
        launch { view.showPosts(posts) }
    }

    override fun somethingWentWrong() {
        launch { view.somethingWentWrong() }
    }
}