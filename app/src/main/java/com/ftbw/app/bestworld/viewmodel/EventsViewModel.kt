package com.ftbw.app.bestworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ftbw.app.bestworld.model.EventDTO
import com.ftbw.app.bestworld.model.EventRecyclerDTO
import com.ftbw.app.bestworld.repository.EventsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EventsRepository(application)

    val listEventRecycler: LiveData<List<EventRecyclerDTO>>
    val event: LiveData<EventDTO>

    init {
        this.listEventRecycler = repository.listEventRecycler
        this.event = repository.event
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
}