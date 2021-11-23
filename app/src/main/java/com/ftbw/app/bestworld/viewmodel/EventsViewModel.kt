package com.ftbw.app.bestworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ftbw.app.bestworld.model.event.EventDTO
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.ftbw.app.bestworld.repository.EventsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EventsRepository(application)

    val listEventRecycler: LiveData<List<EventRecyclerDTO>>
    val listCreatedEvents: LiveData<List<EventRecyclerDTO>>
    val event: LiveData<EventDTO>
    val isEventSaved: LiveData<Boolean>
    val isUserAlreadySignedUp: LiveData<Boolean>
    val isUserGoingToAssist: LiveData<Boolean>
    val numberOfAssistants: LiveData<Long>

    init {
        this.listEventRecycler = repository.listEventRecycler
        this.listCreatedEvents = repository.listCreatedEvents
        this.event = repository.event
        this.isEventSaved = repository.isEventSaved
        this.isUserAlreadySignedUp = repository.isUserAlreadySignedUp
        this.isUserGoingToAssist = repository.isUserGoingToAssist
        this.numberOfAssistants = repository.numberOfAssistants
    }

    fun getEvents(eventLabel: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getEvents(eventLabel)
        }
    }

    fun getSpecificEvent(eventLabel: String, key: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getSpecificEvent(eventLabel, key)
        }
    }

    fun saveEvent(event: EventDTO){
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveEvent(event)
        }
    }

    fun getCreatedEventsByUser(userKey: String, eventLabel: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getCreatedEventsByUser(userKey, eventLabel)
        }
    }

    fun checkIfUserIsSignedUp(userKey: String, eventKey: String, eventLabel: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.checkIfUserIsSignedUp(userKey, eventKey, eventLabel)
        }
    }

    fun userSignUpInEvent(userKey: String, eventKey: String, eventLabel: String, signUp: Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            repository.userSignUpInEvent(userKey, eventKey, eventLabel, signUp)
        }
    }

    fun updateAssistantCount(eventLabel: String, eventKey: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateAssistantCount(eventLabel, eventKey)
        }
    }
}