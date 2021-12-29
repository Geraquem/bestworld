package com.ftbw.app.bestworld.neworden.view.register

import android.net.Uri
import com.ftbw.app.bestworld.model.user.UserDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class RegisterRepository(val listener: IUser) {

    fun saveUser(user: UserDTO, imageUri: Uri?) {
        if (imageUri != null) {
            saveAndGetProfilePictureInStorage(user, imageUri)
        } else {
            saveCompleteUser(user)
        }
    }

    private fun saveAndGetProfilePictureInStorage(user: UserDTO, imageUri: Uri?) {
        if (imageUri != null) {
            val folder: StorageReference = FirebaseStorage.getInstance().reference
                .child("users").child(user.key)
            val fileName: StorageReference = folder.child("file" + imageUri.lastPathSegment)
            fileName.putFile(imageUri).addOnSuccessListener {
                fileName.downloadUrl.addOnSuccessListener {
                    user.imageURL = it.toString()
                    saveCompleteUser(user)
                }
            }
        }
    }

    private fun saveCompleteUser(user: UserDTO) {
        Firebase.database.reference.child("users").child(user.key).setValue(user)
            .addOnCompleteListener {
                saveUserByType(user.type, user.key)
            }
    }

    private fun saveUserByType(type: String, key: String) {
        Firebase.database.reference.child("usersByTypes").child(type).child(key).setValue(true)
            .addOnCompleteListener {
                when {
                    it.isSuccessful -> listener.userSavedOk()
                    else -> listener.somethingWentWrong()
                }
            }
    }

    interface IUser {
        fun userSavedOk()
        fun somethingWentWrong()
    }
}