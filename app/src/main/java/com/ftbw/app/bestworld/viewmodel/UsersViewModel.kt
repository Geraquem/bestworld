package com.ftbw.app.bestworld.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.model.user.UserDTO
import com.ftbw.app.bestworld.model.user.UserRecyclerDTO
import com.ftbw.app.bestworld.neworden.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UsersRepository(application)

    val isUserSaved: LiveData<Boolean>
    val isUserAdded: LiveData<Boolean>
    val isUserAlreadyAdded: LiveData<Boolean>
    val user: LiveData<UserDTO>
    val creator: LiveData<UserRecyclerDTO>
    val listUsers: LiveData<List<UserRecyclerDTO>>
    val userKey = MutableLiveData<String>()

    init {
        this.isUserSaved = repository.isUserSaved
        this.isUserAdded = repository.isUserAdded
        this.isUserAlreadyAdded = repository.isUserAlreadyAdded
        this.user = repository.user
        this.creator = repository.creator
        this.listUsers = repository.listUsers
    }

    fun saveUser(user: UserDTO, imageUri: Uri?) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveUser(user, imageUri)
        }
    }

    fun getCreatorOfEvent(key: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getCreatorOfEvent(key)
        }
    }

    fun getUser(key: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getUser(key)
        }
    }

    fun getUsersByType(type: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getUsersByType(type)
        }
    }

    fun goToUserProfileByKey(userKey: String) {
        this.userKey.value = userKey
    }

    fun addUserToMyNetwork(userKey: String, add: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addUserToMyNetwork(userKey, add)
        }
    }

    fun checkIfUserIsAlreadyAdded(userKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.checkIfUserIsAlreadyAdded(userKey)
        }
    }
}