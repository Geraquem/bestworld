package com.ftbw.app.bestworld.view.userprofile.tabs.posts

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.ftbw.app.bestworld.model.post.PostDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserProfilePostsRepository(var listener: IUserProfilePosts) {

    fun getUserPosts(userKey: String) {
        val keyList: MutableList<String> = mutableListOf()
        Firebase.database.reference.child("users").child(userKey)
            .child("posts").get().addOnSuccessListener {
                for (post in it.children) {
                    keyList.add(post.key.toString())
                }
                givenListOfKeysGetPosts(keyList)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    private fun givenListOfKeysGetPosts(keyList: List<String>) {
        val posts: MutableList<PostDTO> = mutableListOf()
        Firebase.database.reference.child("posts").get().addOnSuccessListener {
            for (event in it.children) {
                for (key in keyList) {
                    if (event.key == key) {
                        posts.add(event.getValue(PostDTO::class.java)!!)
                    }
                }
            }
            listener.posts(posts)

        }.addOnFailureListener {
            listener.somethingWentWrong()
        }
    }

    interface IUserProfilePosts {
        fun posts(posts: List<PostDTO>)
        fun somethingWentWrong()
    }
}