package com.ftbw.app.bestworld.view.userprofile.tabs.posts

import com.ftbw.app.bestworld.model.post.PostDTO

interface UserProfilePostsView {
    fun showPosts(posts: List<PostDTO>)
    fun somethingWentWrong()
}