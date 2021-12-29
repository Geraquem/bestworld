package com.ftbw.app.bestworld.neworden.repository

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

object ImageController {

    fun selectImageFromGallery(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        launcher.launch(intent)
    }
}