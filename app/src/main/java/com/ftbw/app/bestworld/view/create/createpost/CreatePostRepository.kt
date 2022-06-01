package com.ftbw.app.bestworld.view.create.createpost

import android.net.Uri
import com.ftbw.app.bestworld.model.post.PostDTO
import com.ftbw.app.bestworld.model.user.CreatorDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CreatePostRepository(private val listener: ICreatePost) {

    fun getCreatorOfEvent(userKey: String) {
        Firebase.database.reference.child("users").child(userKey)
            .get().addOnSuccessListener {
                if (it.exists()) {
                    it.getValue(CreatorDTO::class.java)
                        ?.let { creator -> listener.creatorOfEvent(creator) }
                }
            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    fun publishPost(post: PostDTO, imageUri: Uri?) {
        val database = Firebase.database.reference
        val key = database.push().key
        post.key = key
        if (imageUri != null) {
            saveAndGetImageUrlInStorage(post, imageUri)
        } else {
            saveCompletePost(post)
        }
    }

    private fun saveAndGetImageUrlInStorage(post: PostDTO, imageUri: Uri?) {
        if (imageUri != null) {
            val folder: StorageReference = FirebaseStorage.getInstance().reference
                .child("posts").child(post.key!!)
            val fileName: StorageReference = folder.child("file" + imageUri.lastPathSegment)
            fileName.putFile(imageUri).addOnSuccessListener {
                fileName.downloadUrl.addOnSuccessListener {
                    post.imageURL = it.toString()
                    saveCompletePost(post)
                }
            }
        }
    }

    private fun saveCompletePost(post: PostDTO) {
        Firebase.database.reference.child("posts").child(post.key!!).setValue(post)
            .addOnCompleteListener {
                when {
                    it.isSuccessful -> saveCreatedPostByUser(post)
                    else -> listener.somethingWentWrong()
                }
            }
    }

    private fun saveCreatedPostByUser(post: PostDTO) {
        Firebase.database.reference.child("users").child(post.creatorKey!!)
            .child("posts").child(post.key!!).setValue(true)
            .addOnCompleteListener {
                when {
                    it.isSuccessful -> listener.postCreated()
                    else -> listener.somethingWentWrong()
                }
            }
    }

    interface ICreatePost {
        fun creatorOfEvent(creator: CreatorDTO)
        fun postCreated()
        fun somethingWentWrong()
    }
}