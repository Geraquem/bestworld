package com.ftbw.app.bestworld.view.userprofile.tabs.posts

import com.ftbw.app.bestworld.model.post.PostDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserProfilePostsPresenter(var view: UserProfilePostsView) :
    UserProfilePostsRepository.IUserProfilePosts, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val repository by lazy { UserProfilePostsRepository(this) }

    fun getUserPosts(userKey: String) {
        launch(Dispatchers.IO) { repository.getUserPosts(userKey) }
    }

    override fun posts(posts: List<PostDTO>) {
        launch { view.showPosts(posts) }
    }

    override fun somethingWentWrong() {
        launch { view.somethingWentWrong() }
    }
}