package com.ftbw.app.bestworld.model.post

data class PostDTO(
    var key: String? = "",
    var text: String? = "",
    var imageURL: String? = "",
    val creatorName: String? = "",
    val creatorKey: String? = "",
    val creatorImageURL: String? = "",
    var likesCount: Long? = 0,
    var commentsCount: Long? = 0
)