package com.ftbw.app.bestworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ftbw.app.bestworld.model.EventDTO
import com.ftbw.app.bestworld.model.EventRecyclerDTO
import com.ftbw.app.bestworld.repository.EventsRepository
import com.ftbw.app.bestworld.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UsersRepository(application)

//    val user: LiveData<User>
//
//    init {
//        this.user = repository.user
//    }
//
//    fun getUser(type: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            repository.getEvents(eventLabel)
//        }
//    }
}