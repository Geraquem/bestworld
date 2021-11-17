package com.ftbw.app.bestworld.helper

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.view.fragments.UserProfileFragment

class UserHelper {
    companion object {
        fun generateAlertDialog(context: Context, closeSession: UserProfileFragment.CloseSession) {
            context as AppCompatActivity
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage(R.string.wannaCloseSession)
                .setCancelable(false)
                .setPositiveButton(R.string.closeSession) { dialog, _ ->
                    closeSession.closeSession()
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }

            dialogBuilder.create().show()
        }
    }
}