package com.ftbw.app.bestworld.view.main.appbar

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1500)
        setTheme(R.style.Theme_Bestworld)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.appBarMain.bottomAppBar)
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
            R.id.events -> navigateTo(item.title.toString())
            R.id.all_events -> navigateTo(item.title.toString())
            R.id.posts -> navigateTo(item.title.toString())
            R.id.created_events -> navigateTo(item.title.toString())
            R.id.assist_events -> navigateTo(item.title.toString())
            R.id.fav_posts -> navigateTo(item.title.toString())
            R.id.all_users_people -> navigateTo(item.title.toString())
            R.id.all_users_companies -> navigateTo(item.title.toString())
            R.id.user_profile -> navigateTo(item.title.toString())
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun navigateTo(id: String) {}
}