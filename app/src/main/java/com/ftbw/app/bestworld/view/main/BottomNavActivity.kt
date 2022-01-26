package com.ftbw.app.bestworld.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityBottomNavBinding
import com.ftbw.app.bestworld.helper.Common.Companion.LOGIN_ACTIVITY_REQUEST_CODE
import com.ftbw.app.bestworld.helper.Common.Companion.REGISTER_ACTIVITY_REQUEST_CODE
import com.ftbw.app.bestworld.view.createevent.CreateEventActivity
import com.ftbw.app.bestworld.view.events.EventsFragment
import com.ftbw.app.bestworld.view.login.LoginActivity
import com.ftbw.app.bestworld.view.register.RegisterActivity
import com.ftbw.app.bestworld.view.userprofile.UserProfileFragment
import com.ftbw.app.bestworld.view.users.UsersFragment
import com.ftbw.app.bestworld.view.users.tabs.UsersRVTab
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BottomNavActivity : AppCompatActivity(), UserProfileFragment.CloseSession,
    UsersRVTab.IOpenUserProfileFromUsers {

    lateinit var bdg: ActivityBottomNavBinding

    private val presenter by lazy { BottomNavPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1500)
        setTheme(R.style.Theme_Bestworld)
        super.onCreate(savedInstanceState)
        bdg = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        presenter.openFragment(this, EventsFragment())

        bdg.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab1 -> {
                    Toast.makeText(this, "posts", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.tab2 -> {
                    presenter.openFragment(this, EventsFragment())
                    true
                }
                R.id.tab3 -> {
                    if (Firebase.auth.currentUser != null) {
                        openPostActivity.launch(Intent(this, CreateEventActivity::class.java))
                    } else {
                        openPostActivity.launch(Intent(this, LoginActivity::class.java))
                    }
                    true
                }
                R.id.tab4 -> {
                    presenter.openFragment(this, UsersFragment(this))
                    true
                }
                R.id.tab5 -> {
                    if (Firebase.auth.currentUser != null) {
                        presenter.openFragment(
                            this, UserProfileFragment(Firebase.auth.currentUser!!.uid)
                        )
                    } else {
                        openPostActivity.launch(Intent(this, LoginActivity::class.java))
                    }
                    true
                }
                else -> false
            }
        }

//        bdg.bottomNavigation.setOnItemReselectedListener {
//            when (it.itemId) {
//                R.id.tab2 -> {
//                    if (Firebase.auth.currentUser != null) {
//                        openPostActivity.launch(Intent(this, CreateEventActivity::class.java))
//                    } else {
//                        openPostActivity.launch(Intent(this, LoginActivity::class.java))
//                    }
//                }
//                R.id.tab3 -> {
//                    presenter.openFragment(this, UsersFragment())
//                }
//            }
//        }
    }

    private val openPostActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                LOGIN_ACTIVITY_REQUEST_CODE -> {
                    if (result.data?.getBooleanExtra("register", false) == true) {
                        openActivity(RegisterActivity::class.java)
                    } else {
                        presenter.goToUserProfileAsMainUser(this)
                    }

                }
                REGISTER_ACTIVITY_REQUEST_CODE -> {
                    presenter.goToUserProfileAsMainUser(this)
                }
            }
        }

    private fun openActivity(classToGo: Class<*>) {
        openPostActivity.launch(Intent(this, classToGo))
    }

    override fun openUserProfileFromUsers(userKey: String) {
        presenter.openFragment(this, UserProfileFragment(userKey))
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is UserProfileFragment) {
            fragment.setCallBack(this)
        }
    }

    override fun closeSession() {
        Firebase.auth.signOut()
        presenter.openFragment(this, EventsFragment())
        //recreate() -> Another way
    }
}