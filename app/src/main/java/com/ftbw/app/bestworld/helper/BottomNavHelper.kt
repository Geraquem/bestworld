package com.ftbw.app.bestworld.helper

import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.BottomNavActivity
import com.ftbw.app.bestworld.R

class BottomNavHelper {

    companion object {
        fun openFragment(context: BottomNavActivity, fragment: Fragment) {
            context.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}