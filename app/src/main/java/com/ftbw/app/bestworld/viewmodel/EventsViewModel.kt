package com.ftbw.app.bestworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ftbw.app.bestworld.model.EventRecyclerDTO
import com.ftbw.app.bestworld.repository.EventsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EventsRepository(application)

    val listEventRecycler: LiveData<List<EventRecyclerDTO>>

    init {
        this.listEventRecycler = repository.listEventRecycler
    }

    fun getEvents(eventName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getEnvironmentalEvents(eventName)
        }
    }
}