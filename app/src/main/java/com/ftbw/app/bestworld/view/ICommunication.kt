package com.ftbw.app.bestworld.view

import androidx.fragment.app.Fragment

interface ICommunication {
    fun openFragment(fragment: Fragment)
    fun openUserProfileFragment(userKey: String)
    fun closeFragmentSelector()
    fun uploadPost()
    fun createEvent()
    fun closeSession()
}