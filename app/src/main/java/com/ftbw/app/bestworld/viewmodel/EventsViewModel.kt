package com.ftbw.app.bestworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ftbw.app.bestworld.repository.EventsRepository

class EventsViewModel (application: Application) : AndroidViewModel(application) {

    private val repository = EventsRepository(application)

    fun searchItem(terms: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.searchItem(terms)
        }
    }
}