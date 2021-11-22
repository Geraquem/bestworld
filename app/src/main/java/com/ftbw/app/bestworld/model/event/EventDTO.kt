package com.ftbw.app.bestworld.model.event

data class EventDTO(
    var key: String? = "",
    val label: String? = "",
    val title: String? = "",
    val description: String? = "",
    val otherInfo: String? = "",
    val address: String? = "",
    val imageURL: String? = "",
    val creatorName: String? = "",
    val creatorKey: String? = "",
    val date: String? = "",
    val time: String? = "",

)