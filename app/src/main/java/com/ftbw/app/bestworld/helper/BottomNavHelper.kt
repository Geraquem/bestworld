package com.ftbw.app.bestworld.helper

import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.view.activity.BottomNavActivity
import com.ftbw.app.bestworld.view.fragments.events.EventsFragment
import com.ftbw.app.bestworld.view.fragments.userprofile.UserProfileFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BottomNavHelper {

    companion object {

        const val LOGIN_ACTIVITY_REQUEST_CODE = 1
        const val REGISTER_ACTIVITY_REQUEST_CODE = 2
        const val CREATE_EVENT_ACTIVITY_REQUEST_CODE = 3

        fun openFragment(context: BottomNavActivity, fragment: Fragment) {
            context.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        fun goToUserProfileAsMainUser(context: BottomNavActivity) {
            if (Firebase.auth.currentUser != null) {
                openFragment(
                    context,
                    UserProfileFragment(Firebase.auth.currentUser!!.uid)
                )
            } else {
                openFragment(context, EventsFragment())
            }
        }
    }
}