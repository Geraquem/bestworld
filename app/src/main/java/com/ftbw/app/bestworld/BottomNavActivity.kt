package com.ftbw.app.bestworld

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ftbw.app.bestworld.databinding.ActivityBottomNavBinding
import com.ftbw.app.bestworld.helper.BottomNavHelper.Companion.openFragment
import com.ftbw.app.bestworld.view.EventsFragment

class BottomNavActivity : AppCompatActivity() {

    lateinit var bdg: ActivityBottomNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        openFragment(this, EventsFragment())

        bdg.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab1 -> {
                    openFragment(this, EventsFragment())
                    true
                }
                R.id.tab2 -> {
                    Toast.makeText(application.applicationContext, "tab add", Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                R.id.tab3 -> {
                    Toast.makeText(application.applicationContext, "tab users", Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                R.id.tab4 -> {
                    Toast.makeText(
                        application.applicationContext,
                        "tab profile",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                else -> false
            }
        }

        bdg.bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.tab1 -> {
                    Toast.makeText(applicationContext, "AGAIN", Toast.LENGTH_SHORT).show()
                }
                R.id.tab2 -> {
                    // Respond to navigation item 2 reselection
                }
            }
        }

    }
}