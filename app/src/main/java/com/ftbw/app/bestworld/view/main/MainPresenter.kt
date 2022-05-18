package com.ftbw.app.bestworld.view.main

import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainPresenter(val context: MainActivity) {

    fun openFragment(fragment: Fragment) {
        val fragmentName = fragment.javaClass.name

//        val lastFrag = context.supportFragmentManager.backStackEntryCount - 1
//        var fragAlreadyInStack = ""
//        if (lastFrag > 0) {
//            fragAlreadyInStack =
//                context.supportFragmentManager.getBackStackEntryAt(lastFrag).name.toString()
//        }
//
//        if (fragAlreadyInStack == fragmentName) {
//            context.supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, fragment)
//                .addToBackStack(fragmentName)
//                .commit()
//        } else {
//            context.supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, fragment)
//                .commit()
//        }

        context.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragmentName)
            .commit()
    }

    fun checkIfUserExist(): Boolean {
        return Firebase.auth.currentUser != null
    }

//
//    fun openSelectorFragment(context: BottomNavActivity, fragment: Fragment) {
//        context.supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_selector_container, fragment)
//            .addToBackStack(null)
//            .commit()
//    }
//
//    fun goToUserProfileAsMainUser(context: BottomNavActivity) {
//        if (Firebase.auth.currentUser != null) {
//            openFragment(context, UserProfileFragment(Firebase.auth.currentUser!!.uid))
//        } else {
//            openFragment(context, EventsFragment())
//        }
//    }
}