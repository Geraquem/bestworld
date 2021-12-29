package com.ftbw.app.bestworld.neworden.view.eventfile

import com.ftbw.app.bestworld.model.event.EventDTO
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.ASSISTANT_EVENTS
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EventFileRepository(private val listener: IEventFile) {

    fun getSpecificEvent(eventLabel: String, eventKey: String) {
        Firebase.database.reference.child("events").child(eventLabel).child(eventKey)
            .get().addOnSuccessListener {
                if (it.exists()) {
                    getAssistantCount(eventLabel, eventKey, it.getValue(EventDTO::class.java))
                }

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    private fun getAssistantCount(eventLabel: String, key: String, event: EventDTO?) {
        Firebase.database.reference.child("events").child(eventLabel)
            .child(key).child("assistants")
            .get().addOnSuccessListener {
                if (event != null) {
                    event.assistantsCount = it.childrenCount
                    checkIfUserIsSignedUp(event)
                }

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    private fun checkIfUserIsSignedUp(event: EventDTO) {
        if (Firebase.auth.currentUser == null) {
            listener.eventDataOk(event, false, false)
        } else {
            var isSignedUp = false
            Firebase.database.reference.child("events").child(event.label!!).child(event.key!!)
                .child("assistants")
                .get().addOnSuccessListener {
                    for (key in it.children) {
                        if (Firebase.auth.currentUser!!.uid == key.key) {
                            isSignedUp = true
                        }
                    }
                    listener.eventDataOk(event, true, isSignedUp)

                }.addOnFailureListener {
                    listener.somethingWentWrong()
                }
        }
    }

    fun userSignUpInEvent(userKey: String, eventLabel: String, eventKey: String, signUp: Boolean) {
        val reference = Firebase.database.reference.child("events").child(eventLabel)
            .child(eventKey).child("assistants").child(userKey)
        if (signUp) {
            reference.setValue(true).addOnCompleteListener {
                if (it.isSuccessful) {
                    saveEventInUserProfile(userKey, eventLabel, eventKey, signUp)
                }
            }
        } else {
            reference.removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    saveEventInUserProfile(userKey, eventLabel, eventKey, signUp)
                }
            }
        }
    }

    private fun saveEventInUserProfile(
        userKey: String,
        eventLabel: String,
        eventKey: String,
        assist: Boolean
    ) {
        val reference = Firebase.database.reference.child("users").child(userKey)
            .child(ASSISTANT_EVENTS).child(eventLabel).child(eventKey)

        if (assist) {
            reference.setValue(true).addOnSuccessListener {
                updateAssistantCount(eventLabel, eventKey, true)
            }
        } else {
            reference.removeValue().addOnSuccessListener {
                updateAssistantCount(eventLabel, eventKey, false)
            }
        }
    }

    private fun updateAssistantCount(eventLabel: String, eventKey: String, signedUp: Boolean) {
        Firebase.database.reference.child("events").child(eventLabel)
            .child(eventKey).child("assistants")
            .get().addOnSuccessListener {
                listener.userSignedUpInEvent(it.childrenCount, signedUp)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    interface IEventFile {
        fun eventDataOk(event: EventDTO, userExists: Boolean, isUserSignedUp: Boolean)
        fun userSignedUpInEvent(assistants: Long, signedUp: Boolean)
        fun somethingWentWrong()
    }
}