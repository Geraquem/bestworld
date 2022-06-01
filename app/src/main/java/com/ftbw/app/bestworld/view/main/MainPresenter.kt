package com.ftbw.app.bestworld.view.main

import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.model.user.UserDTO
import com.ftbw.app.bestworld.view.ICommunication
import com.ftbw.app.bestworld.view.create.SelectorFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class MainPresenter(val context: MainActivity, val view: MainView) : MainRepository.IUserData,
    CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val repository by lazy { MainRepository(this) }

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

    fun openAddFragment(listener: ICommunication) {
        context.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_add, SelectorFragment(listener))
            .addToBackStack(null)
            .commit()
    }

    fun checkIfUserExist(): Boolean {
        return Firebase.auth.currentUser != null
    }

//    fun openSelectorFragment(context: BottomNavActivity, fragment: Fragment) {
//        context.supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_selector_container, fragment)
//            .addToBackStack(null)
//            .commit()
//    }

    fun getUserData(userKey: String) {
        repository.getUserData(userKey)
    }

    override fun userData(user: UserDTO) {
        view.showUserData(user)
    }

    override fun cantFindUser() {
        view.cantFindUser()
    }
}