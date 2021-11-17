package com.ftbw.app.bestworld.helper

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.view.fragments.UserProfileFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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

        fun checkIfisMainUser(userKey: String, addButton: TextView) {
            if (userKey == Firebase.auth.currentUser!!.uid) {
                addButton.visibility = View.GONE
                //editProfileButton.visibility = View.GONE
            } else {
                addButton.visibility = View.VISIBLE
                //editProfileButton.visibility = View.VISIBLE
            }
        }
    }
}