package com.ftbw.app.bestworld.view.main

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityMainBinding
import com.ftbw.app.bestworld.helper.EventCommon.Companion.LOGIN_ACTIVITY_REQUEST_CODE
import com.ftbw.app.bestworld.helper.EventCommon.Companion.REGISTER_ACTIVITY_REQUEST_CODE
import com.ftbw.app.bestworld.model.user.UserDTO
import com.ftbw.app.bestworld.view.ICommunication
import com.ftbw.app.bestworld.view.create.createevent.CreateEventActivity
import com.ftbw.app.bestworld.view.create.createpost.CreatePostActivity
import com.ftbw.app.bestworld.view.events.category.EventCategoryFragment
import com.ftbw.app.bestworld.view.login.LoginActivity
import com.ftbw.app.bestworld.view.posts.PostsFragment
import com.ftbw.app.bestworld.view.register.RegisterActivity
import com.ftbw.app.bestworld.view.userprofile.UserProfileFragment
import com.ftbw.app.bestworld.view.users.UsersFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    MainView, ICommunication {

    private lateinit var binding: ActivityMainBinding

    private val presenter by lazy { MainPresenter(this, this) }

    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var userKey: String

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

        userKey = if (presenter.checkIfUserExist()) Firebase.auth.currentUser!!.uid else "null"
        presenter.getUserData(userKey)

        binding.appBarMain.floatingButton.setOnClickListener {
            if (presenter.checkIfUserExist()) {
                presenter.openAddFragment(this)
            } else {
                openActivity(LoginActivity::class.java)
            }
        }

//        binding.appBarMain.bottomAppBar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.events -> {
//                    Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.posts -> {
//                    Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
//                    true
//                }
//                else -> false
//            }
//        }
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
            R.id.events -> openFragment(EventCategoryFragment(this, true))
            R.id.posts -> openFragment(PostsFragment(this))
            R.id.created_events -> {/* checkIfUserExist(Whatever()) */
            }
            R.id.assist_events -> {/* checkIfUserExist(Whatever()) */
            }
            R.id.fav_posts -> { /* checkIfUserExist(FavPostsFragment()) */
            }
            R.id.all_users_people -> openFragment(UsersFragment(this, 0))
            R.id.all_users_companies -> openFragment(UsersFragment(this, 1))
            R.id.user_profile -> {
                if (presenter.checkIfUserExist()) {
                    openUserProfileFragment(Firebase.auth.currentUser!!.uid)
                } else {
                    openActivity(LoginActivity::class.java)
                }
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun openFragment(fragment: Fragment) {
        presenter.openFragment(fragment)
    }

    override fun openUserProfileFragment(userKey: String) {
        presenter.openFragment(UserProfileFragment(userKey, this))
    }

    override fun closeFragmentSelector() {
        supportFragmentManager.popBackStack()
    }

    override fun uploadPost() {
        closeFragmentSelector()
        openActivity(CreatePostActivity::class.java)
    }

    override fun createEvent() {
        closeFragmentSelector()
        openActivity(CreateEventActivity::class.java)
    }

    private fun openActivity(classToGo: Class<*>) {
        openPostActivity.launch(Intent(this, classToGo))
    }

    private val openPostActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                LOGIN_ACTIVITY_REQUEST_CODE -> {
                    if (result.data?.getBooleanExtra("register", false) == true) {
                        openActivity(RegisterActivity::class.java)
                    } else {
                        presenter.getUserData(Firebase.auth.currentUser!!.uid)
                    }
                }
                REGISTER_ACTIVITY_REQUEST_CODE -> {
                    presenter.getUserData(Firebase.auth.currentUser!!.uid)
                }
            }
        }

    override fun showUserData(user: UserDTO) {
        val header = binding.navigationView.getHeaderView(0)
        header.findViewById<LinearLayout>(R.id.linear_welcome).visibility = View.GONE
        header.findViewById<LinearLayout>(R.id.linear_user_signed).visibility = View.VISIBLE
        header.findViewById<TextView>(R.id.userName).text = user.name
        header.findViewById<TextView>(R.id.userEmail).text = user.email
        header.findViewById<TextView>(R.id.networkCount).text =
            getString(R.string.myNetwork, user.addedCount.toString())
        Glide.with(this).load(user.imageURL).into(header.findViewById(R.id.dh_image))

        presenter.openFragment(PostsFragment(this))
    }

    override fun cantFindUser() {
        val header = binding.navigationView.getHeaderView(0)
        header.findViewById<LinearLayout>(R.id.linear_user_signed).visibility = View.GONE
        header.findViewById<LinearLayout>(R.id.linear_welcome).visibility = View.VISIBLE

        presenter.openFragment(PostsFragment(this))
    }

    override fun closeSession() {
        FirebaseAuth.getInstance().signOut()
        openFragment(PostsFragment(this))
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        cantFindUser()
    }
}