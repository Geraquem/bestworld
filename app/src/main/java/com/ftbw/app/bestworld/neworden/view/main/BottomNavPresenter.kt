package com.ftbw.app.bestworld.neworden.view.main

import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.neworden.view.events.EventsFragment
import com.ftbw.app.bestworld.neworden.view.userprofile.UserProfileFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BottomNavPresenter {

    fun openFragment(context: BottomNavActivity, fragment: Fragment) {
        context.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun goToUserProfileAsMainUser(context: BottomNavActivity) {
        if (Firebase.auth.currentUser != null) {
            openFragment(context, UserProfileFragment(Firebase.auth.currentUser!!.uid))
        } else {
            openFragment(context, EventsFragment())
        }
    }
}