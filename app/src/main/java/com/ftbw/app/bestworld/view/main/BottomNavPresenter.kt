package com.ftbw.app.bestworld.view.main

import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.view.events.EventsFragment
import com.ftbw.app.bestworld.view.userprofile.UserProfileFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BottomNavPresenter {

    fun openFragment(context: BottomNavActivity, fragment: Fragment) {
        val fragmentName = fragment.javaClass.name

        /**
         *
         *
         *
         *
         * CHECKKKKKKKKKKKKKKKKKKKKKK
         *
         *
         *
         **/



        val lastFrag = context.supportFragmentManager.backStackEntryCount - 1
        var fragAlreadyInStack = ""
        if (lastFrag > 0) {
            fragAlreadyInStack =
                context.supportFragmentManager.getBackStackEntryAt(lastFrag).name.toString()
        }

        if (fragAlreadyInStack == fragmentName) {
            context.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragmentName)
                .commit()
        } else {
            context.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        context.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragmentName)
            .commit()

        //evitar que se añadan mismos fragments a la backstack si son iguales y se pulsa el mismo boton varias veces
    }

    fun openSelectorFragment(context: BottomNavActivity, fragment: Fragment) {
        context.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_selector_container, fragment)
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