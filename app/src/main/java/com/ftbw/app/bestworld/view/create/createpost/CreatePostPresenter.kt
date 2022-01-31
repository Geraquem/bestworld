package com.ftbw.app.bestworld.view.create.createpost

import android.net.Uri
import com.ftbw.app.bestworld.model.post.PostDTO
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CreatePostPresenter(val view: CreatePostView) : CreatePostRepository.ICreatePost,
    CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val repository by lazy { CreatePostRepository(this) }

    fun getCreatorOfEvent(userKey: String) {
        launch(Dispatchers.IO) {
            repository.getCreatorOfEvent(userKey)
        }
    }

    fun publishPost(imageUri: Uri?, text: String, userName: String) {
        if (text.isEmpty()) {
            view.setErrorMessage()
        } else {
            val post = PostDTO(
                "",
                text,
                "",
                userName,
                Firebase.auth.currentUser!!.uid,
                0,
                0
            )
            launch(Dispatchers.IO) {
                repository.publishPost(post, imageUri)
            }
        }
    }

    override fun creatorOfEvent(name: String) {
        launch { view.creatorOfEvent(name) }
    }

    override fun postCreated() {
        launch { view.postCreated() }
    }

    override fun somethingWentWrong() {
        launch { view.somethingWentWrong() }
    }
}