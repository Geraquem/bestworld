package com.ftbw.app.bestworld.view.posts

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PostsRepository(private val listener: IPosts) {

//    fun getEventsByLabel(label: String) {
//        val events: MutableList<EventRecyclerDTO> = mutableListOf()
//        Firebase.database.reference.child("events").child(label)
//            .get().addOnSuccessListener {
//                events.clear()
//                for (event in it.children) {
//                    events.add(event.getValue(EventRecyclerDTO::class.java)!!)
//                }
////                listener.showEvents(events)
//
//            }.addOnFailureListener {
//                listener.somethingWentWrong()
//            }
//    }

    interface IPosts {
//        fun showPosts(events: List<PostDTO>)
        fun somethingWentWrong()
    }
}