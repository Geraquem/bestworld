package com.ftbw.app.bestworld.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityBottomNavBinding
import com.ftbw.app.bestworld.helper.BottomNavHelper.Companion.LOGIN_ACTIVITY_REQUEST_CODE
import com.ftbw.app.bestworld.helper.BottomNavHelper.Companion.REGISTER_ACTIVITY_REQUEST_CODE
import com.ftbw.app.bestworld.helper.BottomNavHelper.Companion.goToUserProfileAsMainUser
import com.ftbw.app.bestworld.helper.BottomNavHelper.Companion.openFragment
import com.ftbw.app.bestworld.view.fragments.UserProfileFragment
import com.ftbw.app.bestworld.view.fragments.events.EventsFragment
import com.ftbw.app.bestworld.view.fragments.users.UsersFragment
import com.ftbw.app.bestworld.viewmodel.UsersViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BottomNavActivity : AppCompatActivity(), UserProfileFragment.CloseSession {

    lateinit var bdg: ActivityBottomNavBinding

    private lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1500)
        setTheme(R.style.Theme_Bestworld)
        super.onCreate(savedInstanceState)
        bdg = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        openFragment(this, EventsFragment())

        bdg.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab1 -> {
                    openFragment(this, EventsFragment())
                    true
                }
                R.id.tab2 -> {
                    if (Firebase.auth.currentUser != null) {
                        openPostActivity.launch(Intent(this, CreateEventActivity::class.java))
                    } else {
                        openPostActivity.launch(Intent(this, LoginActivity::class.java))
                    }
                    true
                }
                R.id.tab3 -> {
                    openFragment(this, UsersFragment())
                    true
                }
                R.id.tab4 -> {
                    if (Firebase.auth.currentUser != null) {
                        openFragment(
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

        bdg.bottomNavigation.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.tab2 -> {
                    if (Firebase.auth.currentUser != null) {
                        openPostActivity.launch(Intent(this, CreateEventActivity::class.java))
                    } else {
                        openPostActivity.launch(Intent(this, LoginActivity::class.java))
                    }
                }
                R.id.tab3 -> {
                    openFragment(this, UsersFragment())
                }
            }
        }

        usersViewModel.userKey.observe(this, {
            openFragment(this, UserProfileFragment(it))
        })
    }

    private val openPostActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                LOGIN_ACTIVITY_REQUEST_CODE -> {
                    if (result.data?.getBooleanExtra("register", false) == true) {
                        openActivity(RegisterActivity::class.java)
                    } else {
                        goToUserProfileAsMainUser(this)
                    }

                }
                REGISTER_ACTIVITY_REQUEST_CODE -> {
                    goToUserProfileAsMainUser(this)
                }
            }
        }

    private fun openActivity(classToGo: Class<*>) {
        openPostActivity.launch(Intent(this, classToGo))
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is UserProfileFragment) {
            fragment.setCallBack(this)
        }
    }

    override fun closeSession() {
        Firebase.auth.signOut()
        openFragment(this, EventsFragment())
        //recreate() -> Another way
    }

}