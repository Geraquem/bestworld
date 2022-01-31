package com.ftbw.app.bestworld.view.posts

import com.ftbw.app.bestworld.model.post.PostDTO

interface PostsView {
    fun showPosts(posts: List<PostDTO>)
    fun somethingWentWrong()
}