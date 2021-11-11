package com.ftbw.app.bestworld.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UsersRepository(application)

    var isUserSaved = MutableLiveData<Boolean>()

    init {
        this.isUserSaved = repository.isUserSaved
    }

    fun saveUser(name: String, email: String, key: String, type: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveUser(name, email, key, type)
        }
    }
}