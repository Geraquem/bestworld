package com.ftbw.app.bestworld.view.userprofile.tabs

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import replaceBrackets

class UserProfileRVTabRepository(var listener: IUserProfileRVTab) {

    /**
    fun getAllEventsRelatedWithUser(relation: String, userKey: String) {
        val hashMap = hashMapOf<String, List<*>>()
        Firebase.database.reference.child("users").child(userKey)
            .child(relation).get()
            .addOnSuccessListener {
                for (label in it.children) {
                    hashMap[label.key.toString()] = listOf((label.value as HashMap<*, *>).keys)
                }
                getAllEvents(hashMap)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    private fun getAllEvents(hashMap: HashMap<String, List<*>>) {
        val events: MutableList<EventRecyclerDTO> = mutableListOf()
        for (label in hashMap.keys) {
            Firebase.database.reference.child("events").child(label).get().addOnSuccessListener {
                for (child in it.children) {
                    val aux = hashMap.values
                    aux.forEach {
                        println(" -------------> " + it.toString().replaceBrackets()) //no funciona
                    }
//                    if (aux.forEach().equals(child.key)){
//
//                    }
//                    if (hashMap.values.toString().replaceBrackets() == child.key) {
//                        println("---------->>>>>>>>>> " + child.key)
//                    }
                }
            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
        }
//
//        println("-------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
//        println(hashMap["environmental"].toString().replace("[", "").replace("]", ""))
    }

    **/

    fun getEventsRelatedWithUser(relation: String, userKey: String, eventLabel: String) {
        val keyList: MutableList<String> = mutableListOf()
        Firebase.database.reference.child("users").child(userKey)
            .child(relation).child(eventLabel).get()
            .addOnSuccessListener {
                keyList.clear()
                for (event in it.children) {
                    keyList.add(event.key.toString())
                }
                givenListOfKeysGetEvents(keyList, eventLabel)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    private fun givenListOfKeysGetEvents(keyList: List<String>, eventLabel: String) {
        val events: MutableList<EventRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("events").child(eventLabel)
            .get().addOnSuccessListener {
                events.clear()
                for (event in it.children) {
                    for (key in keyList) {
                        if (event.key == key) {
                            events.add(event.getValue(EventRecyclerDTO::class.java)!!)
                        }
                    }
                }
                listener.events(events)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    interface IUserProfileRVTab {
        fun events(events: List<EventRecyclerDTO>)
        fun somethingWentWrong()
    }
}