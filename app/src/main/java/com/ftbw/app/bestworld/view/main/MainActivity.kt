package com.ftbw.app.bestworld.view.main

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityMainBinding
import com.ftbw.app.bestworld.view.ICommunication
import com.ftbw.app.bestworld.view.events.category.EventCategoryFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ICommunication {

    private lateinit var binding: ActivityMainBinding

    private val presenter by lazy { MainPresenter(this) }

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1500)
        setTheme(R.style.Theme_Bestworld)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            binding.appBarMain.bottomAppBar,
            R.string.dm_opened,
            R.string.dm_closed
        )
        binding.drawerLayout.addDrawerListener(toggle)
        binding.navigationView.setNavigationItemSelectedListener(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        binding.appBarMain.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.events -> {
                    Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.posts -> {
                    Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.all_events -> openFragment(EventCategoryFragment(this, false))
            R.id.events -> openFragment(EventCategoryFragment(this, true)) //modificar
            R.id.posts -> {}
            R.id.created_events -> {}
            R.id.assist_events -> {}
            R.id.fav_posts -> {}
            R.id.all_users_people -> {}
            R.id.all_users_companies -> {}
            R.id.user_profile -> {}
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun openFragment(fragment: Fragment) {
        presenter.openFragment(fragment)
    }
}