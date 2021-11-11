package com.ftbw.app.bestworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.model.UserDTO
import com.ftbw.app.bestworld.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UsersRepository(application)

    var isUserSaved = MutableLiveData<Boolean>()
    var user = MutableLiveData<UserDTO>()

    init {
        this.isUserSaved = repository.isUserSaved
        this.user = repository.user
    }

    fun saveUser(name: String, email: String, key: String, type: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveUser(name, email, key, type)
        }
    }

    fun getUser(key: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getUser(key)
        }
    }
}