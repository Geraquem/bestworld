package com.ftbw.app.bestworld.helper

import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.view.activity.BottomNavActivity
import com.ftbw.app.bestworld.R

class EventHelper {
    companion object {

        fun getListOfEvents(context: BottomNavActivity, fragment: Fragment) {
            context.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}