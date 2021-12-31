package com.ftbw.app.bestworld.view.createevent

import android.net.Uri
import com.ftbw.app.bestworld.model.event.EventDTO
import com.ftbw.app.bestworld.helper.Common.Companion.CREATED_EVENTS
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CreateEventRepository(private val listener: ICreateEvent) {

    fun getCreatorOfEvent(userKey: String) {
        Firebase.database.reference.child("users").child(userKey).child("name")
            .get().addOnSuccessListener {
                if (it.exists()) {
                    listener.creatorOfEvent(it.value.toString())
                }
            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    fun createEvent(event: EventDTO, imageUri: Uri?) {
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
            .child(event.label!!).child(event.key!!).setValue(event)
            .addOnCompleteListener {
                when {
                    it.isSuccessful -> saveCreatedEventByUser(event)
                    else -> listener.somethingWentWrong()
                }
            }
    }

    private fun saveCreatedEventByUser(event: EventDTO) {
        Firebase.database.reference.child("users").child(event.creatorKey!!)
            .child(CREATED_EVENTS).child(event.label!!).child(event.key!!).setValue(true)
            .addOnCompleteListener {
                when {
                    it.isSuccessful -> listener.eventCreated()
                    else -> listener.somethingWentWrong()
                }
            }
    }

    interface ICreateEvent {
        fun creatorOfEvent(name: String)
        fun eventCreated()
        fun somethingWentWrong()
    }
}