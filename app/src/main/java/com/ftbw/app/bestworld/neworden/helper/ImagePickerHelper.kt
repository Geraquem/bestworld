package com.ftbw.app.bestworld.neworden.helper

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

object ImagePickerHelper {

    fun selectImageFromGallery(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        launcher.launch(intent)
    }
}