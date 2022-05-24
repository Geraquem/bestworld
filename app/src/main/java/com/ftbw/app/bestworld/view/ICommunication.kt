package com.ftbw.app.bestworld.view

import androidx.fragment.app.Fragment

interface ICommunication {
    fun openFragment(fragment: Fragment)
    fun closeFragmentSelector()
    fun uploadPost()
    fun createEvent()
}