package com.ftbw.app.bestworld.repository

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.helper.UserHelper.Companion.ASSISTANT_EVENTS
import com.ftbw.app.bestworld.helper.UserHelper.Companion.CREATED_EVENTS
import com.ftbw.app.bestworld.model.event.EventDTO
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EventsRepository constructor(val application: Application) {

    val listEventRecycler = MutableLiveData<List<EventRecyclerDTO>>()
    val listOfEvents = MutableLiveData<List<EventRecyclerDTO>>()
    val event = MutableLiveData<EventDTO>()
    val isEventSaved = MutableLiveData<Boolean>()
    val isUserAlreadySignedUp = MutableLiveData<Boolean>()
    val isUserGoingToAssist = MutableLiveData<Boolean>()
    val numberOfAssistants = MutableLiveData<Long>()

    fun getAllEvents(){

    }

    fun getEvents(eventLabel: String) {
        val auxList: MutableList<EventRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("events").child(eventLabel).get()
            .addOnSuccessListener {
                auxList.clear()
                for (event in it.children) {
                    auxList.add(event.getValue(EventRecyclerDTO::class.java)!!)
                }
                listEventRecycler.value = auxList

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    fun getSpecificEvent(eventLabel: String, key: String) {
        Firebase.database.reference.child("events").child(eventLabel).child(key).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    getAssistantCount(eventLabel, key, it.getValue(EventDTO::class.java))
                }

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    private fun getAssistantCount(eventLabel: String, key: String, event: EventDTO?) {
        Firebase.database.reference.child("events").child(eventLabel)
            .child(key).child("assistants").get()
            .addOnSuccessListener {
                if (event != null) {
                    event.assistantsCount = it.childrenCount
                    this.event.value = event
                }

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    fun saveEvent(event: EventDTO, imageUri: Uri?) {
        val database = Firebase.database.reference
        val label = event.label
        val key = database.child(label!!).push().key
        event.key = key

        if (imageUri != null) {
            saveAndGetImageUrlInStorage(event, imageUri)
        } else {
            saveCompleteEvent(event)
        }
    }

    private fun saveAndGetImageUrlInStorage(event: EventDTO, imageUri: Uri?) {
        if (imageUri != null && event.label != null) {
            val folder: StorageReference = FirebaseStorage.getInstance().reference
                .child("events").child(event.label).child(event.key!!)
            val fileName: StorageReference = folder.child("file" + imageUri.lastPathSegment)
            fileName.putFile(imageUri).addOnSuccessListener {
                fileName.downloadUrl.addOnSuccessListener {
                    event.imageURL = it.toString()
                    saveCompleteEvent(event)
                }
            }
        }
    }

    private fun saveCompleteEvent(event: EventDTO) {
        Firebase.database.reference.child("events")
            .child(event.label!!).child(event.key!!).setValue(event).addOnCompleteListener {
                saveCreatedEventByUser(event)
            }
    }

    private fun saveCreatedEventByUser(event: EventDTO) {
        Firebase.database.reference.child("users").child(event.creatorKey!!)
            .child(CREATED_EVENTS).child(event.label!!)
            .child(event.key!!).setValue(true).addOnCompleteListener {
                isEventSaved.value = it.isSuccessful
            }
    }

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
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    private fun givenListOfKeysGetEvents(keyList: List<String>, eventLabel: String) {
        val auxList: MutableList<EventRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("events").child(eventLabel).get()
            .addOnSuccessListener {
                auxList.clear()
                for (event in it.children) {
                    for (key in keyList) {
                        if (event.key == key) {
                            auxList.add(event.getValue(EventRecyclerDTO::class.java)!!)
                        }
                    }
                }
                listOfEvents.value = auxList

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    fun checkIfUserIsSignedUp(userKey: String, eventKey: String, eventLabel: String) {
        var isAssistant = false
        Firebase.database.reference.child("events").child(eventLabel)
            .child(eventKey).child("assistants").get()
            .addOnSuccessListener {
                for (key in it.children) {
                    if (userKey == key.key) {
                        isAssistant = true
                    }
                }
                isUserAlreadySignedUp.value = isAssistant

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    fun userSignUpInEvent(userKey: String, eventKey: String, eventLabel: String, assist: Boolean) {
        val reference = Firebase.database.reference.child("events").child(eventLabel)
            .child(eventKey).child("assistants").child(userKey)
        if (assist) {
            reference.setValue(true).addOnCompleteListener {
                if (it.isSuccessful) {
                    saveAssistantEventByUser(userKey, eventKey, eventLabel, assist)
                }
            }
        } else {
            reference.removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    saveAssistantEventByUser(userKey, eventKey, eventLabel, assist)
                }
            }
        }
    }

    private fun saveAssistantEventByUser(
        userKey: String,
        eventKey: String,
        eventLabel: String,
        assist: Boolean
    ) {
        val reference = Firebase.database.reference.child("users").child(userKey)
            .child(ASSISTANT_EVENTS).child(eventLabel).child(eventKey)
        if (assist) {
            reference.setValue(true).addOnSuccessListener {
                isUserGoingToAssist.value = true
            }
        } else {
            reference.removeValue().addOnSuccessListener {
                isUserGoingToAssist.value = false
            }
        }
    }

    fun updateAssistantCount(eventLabel: String, eventKey: String) {
        Firebase.database.reference.child("events").child(eventLabel)
            .child(eventKey).child("assistants").get()
            .addOnSuccessListener {
                numberOfAssistants.value = it.childrenCount

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }
}