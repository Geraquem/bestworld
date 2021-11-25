package com.ftbw.app.bestworld.view.fragments.userprofile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.adapter.pager.UserProfileViewPagerAdapter
import com.ftbw.app.bestworld.databinding.FragmentUserProfileBinding
import com.ftbw.app.bestworld.helper.UserHelper.Companion.checkIfIsMainUser
import com.ftbw.app.bestworld.helper.UserHelper.Companion.generateAlertDialog
import com.ftbw.app.bestworld.viewmodel.EventsViewModel
import com.ftbw.app.bestworld.viewmodel.UsersViewModel
import com.google.android.material.tabs.TabLayoutMediator

class UserProfileFragment(var userKey: String) : Fragment() {

    private var _bdg: FragmentUserProfileBinding? = null
    private val bdg get() = _bdg!!

    lateinit var getContext: Context

    private lateinit var usersViewModel: UsersViewModel
    private lateinit var eventsViewModel: EventsViewModel

    private lateinit var closeSession: CloseSession

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bdg = FragmentUserProfileBinding.inflate(inflater, container, false)
        return bdg.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        val viewPagerAdapter = UserProfileViewPagerAdapter(this, userKey)
        bdg.viewPager.adapter = viewPagerAdapter

        bdg.salir.setOnClickListener {
            generateAlertDialog(getContext, closeSession)
        }

        if (!checkIfIsMainUser(userKey, bdg.addButton)) {
            usersViewModel.checkIfUserIsAlreadyAdded(userKey)
        }
        usersViewModel.isUserAlreadyAdded.observe(viewLifecycleOwner, {
            if (it) {
                bdg.addText.text = getString(R.string.removeFromMyNetwork)
                bdg.addIcon.setBackgroundResource(R.drawable.ic_users_remove)
            } else {
                bdg.addText.text = getString(R.string.addToMyNetwork)
                bdg.addIcon.setBackgroundResource(R.drawable.ic_users_add)
            }
        })

        bdg.loading.root.visibility = View.VISIBLE

        usersViewModel.getUser(userKey)
        usersViewModel.user.observe(viewLifecycleOwner, {
            bdg.name.text = it.name
            setProfilePicture(it.imageURL)
            bdg.email.text = it.email
            bdg.usersAdded.text = it.addedCount.toString()
            bdg.loading.root.visibility = View.GONE
        })

        bdg.addButton.setOnClickListener {
            bdg.addButton.isEnabled = false
            if (bdg.addText.text == getString(R.string.addToMyNetwork)) {
                usersViewModel.addUserToMyNetwork(userKey, true)
            } else {
                usersViewModel.addUserToMyNetwork(userKey, false)
            }
        }
        usersViewModel.isUserAdded.observe(viewLifecycleOwner, {
            if (it) {
                bdg.addText.text = getString(R.string.removeFromMyNetwork)
                bdg.addIcon.setBackgroundResource(R.drawable.ic_users_remove)
            } else {
                bdg.addText.text = getString(R.string.addToMyNetwork)
                bdg.addIcon.setBackgroundResource(R.drawable.ic_users_add)
            }
            bdg.addButton.isEnabled = true
        })

        TabLayoutMediator(bdg.tabLayout, bdg.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_tab_created)
                }
                1 -> {
                    tab.setIcon(R.drawable.ic_event_assistant)
                }
            }
        }.attach()
    }

    private fun setProfilePicture(imageURL: String) {
        if (imageURL == "") {
            bdg.profilePicture.setBackgroundResource(R.drawable.ic_user_name)
        } else {
            Glide.with(getContext).load(imageURL).into(bdg.profilePicture)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getContext = context
    }

    fun setCallBack(close: CloseSession) {
        closeSession = close
    }

    interface CloseSession {
        fun closeSession()
    }
}