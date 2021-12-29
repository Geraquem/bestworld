package com.ftbw.app.bestworld.neworden.view.eventfile

import android.content.Context
import android.view.View
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.model.event.EventDTO
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EventFilePresenter(val view: EventFileView) : EventFileRepository.IEventFile {

    private val repository by lazy { EventFileRepository(this) }

    fun getSpecificEvent(eventLabel: String, eventKey: String) {
        repository.getSpecificEvent(eventLabel, eventKey)
    }

    fun setEventImage(imageURL: String) {
        if (imageURL == "") {
            view.showEventImage(false, imageURL)
        } else {
            view.showEventImage(true, imageURL)
        }
    }

    fun setOtherInfo(otherInfo: String) {
        if (otherInfo.isBlank() && otherInfo.isEmpty()) {
            view.setOtherInfo(View.GONE, otherInfo)
        } else {
            view.setOtherInfo(View.VISIBLE, otherInfo)
        }
    }

    fun doSignUp(context: Context, text: String, eventLabel: String, eventKey: String) {
        val userKey = Firebase.auth.currentUser!!.uid
        if (text == context.getString(R.string.signUpToEvent)) {
            repository.userSignUpInEvent(userKey, eventLabel, eventKey, true)
        } else if (text == context.getString(R.string.signDownToEvent)) {
            repository.userSignUpInEvent(userKey, eventLabel, eventKey, false)
        }
    }

    override fun eventDataOk(event: EventDTO, userExists: Boolean, isUserSignedUp: Boolean) {
        view.setEventData(event, userExists, isUserSignedUp)
    }

    override fun userSignedUpInEvent(assistants: Long, signedUp: Boolean) {
        view.userSignedUpInEvent(assistants, signedUp)
    }

    override fun somethingWentWrong() {
        view.somethingWentWrong()
    }
}