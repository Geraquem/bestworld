package com.ftbw.app.bestworld.helper

import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.view.activity.BottomNavActivity

class BottomNavHelper {

    companion object {

        const val LOGIN_ACTIVITY_REQUEST_CODE = 1
        const val REGISTER_ACTIVITY_REQUEST_CODE = 2

        fun openFragment(context: BottomNavActivity, fragment: Fragment) {
            context.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}