package com.ftbw.app.bestworld.view.userprofile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.FragmentUserProfileBinding
import com.ftbw.app.bestworld.model.user.UserDTO
import com.ftbw.app.bestworld.view.userprofile.adapter.viewpager.UserProfileMainPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class UserProfileFragment(var userKey: String) : Fragment(), UserProfileView {

    private var _bdg: FragmentUserProfileBinding? = null
    private val bdg get() = _bdg!!

    lateinit var mContext: Context

    private val presenter by lazy { UserProfilePresenter(this) }

    private lateinit var closeSession: CloseSession

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bdg = FragmentUserProfileBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bdg.loading.root.visibility = View.VISIBLE

        val viewPagerAdapter = UserProfileMainPagerAdapter(this, userKey)
        bdg.viewPager.adapter = viewPagerAdapter

        bdg.closeSession.setOnClickListener { showAlertDialog() }
        bdg.settings.setOnClickListener { /* open editProfile */ }

        presenter.checkIfIsMainUserProfile(userKey)

        bdg.addButton.setOnClickListener {
            bdg.addButton.isEnabled = false
            if (bdg.addText.text == getString(R.string.addToMyNetwork)) {
                presenter.addUserToMyNetwork(userKey, true)
            } else {
                presenter.addUserToMyNetwork(userKey, false)
            }
        }

        TabLayoutMediator(bdg.tabLayout, bdg.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_nav_posts)
//                    tab.setIcon(R.drawable.ic_tab_created)
                }
                1 -> {
                    tab.setIcon(R.drawable.ic_nav_events)
//                    tab.setIcon(R.drawable.ic_event_assistant)
                }
            }
        }.attach()
    }

    private fun setProfilePicture(imageURL: String) {
        if (imageURL == "") {
            bdg.profilePicture.setBackgroundResource(R.drawable.ic_user_name)
        } else {
            Glide.with(mContext).load(imageURL).into(bdg.profilePicture)
        }
    }

    override fun setUserData(user: UserDTO) {
        bdg.name.text = user.name
        setProfilePicture(user.imageURL)
        bdg.email.text = user.email
        bdg.usersAdded.text = user.addedCount.toString()
        bdg.loading.root.visibility = View.GONE
    }

    override fun showLinearButtons(view: Int) {
        bdg.linearButtons.visibility = view
    }

    override fun showAddButton(view: Int) {
        bdg.addButton.visibility = view
    }

    override fun modifyAddButton(isAdded: Boolean) {
        when (isAdded) {
            true -> {
                bdg.addText.text = getString(R.string.removeFromMyNetwork)
                bdg.addIcon.setBackgroundResource(R.drawable.ic_users_remove)
            }
            false -> {
                bdg.addText.text = getString(R.string.addToMyNetwork)
                bdg.addIcon.setBackgroundResource(R.drawable.ic_users_add)
            }
        }
        bdg.addButton.isEnabled = true
    }

    override fun somethingWentWrong() {
        Toast.makeText(mContext, getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(mContext)
        dialogBuilder.setMessage(R.string.wannaCloseSession)
            .setCancelable(false)
            .setPositiveButton(R.string.closeSession) { dialog, _ ->
                closeSession.closeSession()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }

        dialogBuilder.create().show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    fun setCallBack(close: CloseSession) {
        closeSession = close
    }

    interface CloseSession {
        fun closeSession()
    }
}