package com.ftbw.app.bestworld.view.posts

import com.ftbw.app.bestworld.model.post.PostDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PostsRepository(private val listener: IPosts) {

    fun getAllPosts(userId: String) {
        val userKeyList = mutableListOf<String>()
        Firebase.database.reference.child("users").child(userId)
            .child("added").get().addOnSuccessListener {
                for (key in it.children) {
                    userKeyList.add(key.key.toString())
                }
                getPostsFromUserKey(userKeyList)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    private fun getPostsFromUserKey(userKeyList: List<String>) {
        val postKeyList = mutableListOf<String>()
        for (userKey in userKeyList) {
            Firebase.database.reference.child("users").child(userKey)
                .child("posts").get().addOnSuccessListener {
                    for (postKey in it.children) {
                        postKeyList.add(postKey.key.toString())
                    }
                    getAllPostsFromKeyList(postKeyList)

                }.addOnFailureListener {
                    listener.somethingWentWrong()
                }
        }
    }

    private fun getAllPostsFromKeyList(postKeyList: List<String>) {
        val posts = mutableListOf<PostDTO>()
        Firebase.database.reference.child("posts").get().addOnSuccessListener {
            for (post in it.children) {
                if (postKeyList.contains(post.key)) {
                    posts.add(post.getValue(PostDTO::class.java)!!)
                }
            }
            listener.showPosts(posts)

        }.addOnFailureListener {
            listener.somethingWentWrong()
        }
    }

    interface IPosts {
        fun showPosts(posts: List<PostDTO>)
        fun somethingWentWrong()
    }
}