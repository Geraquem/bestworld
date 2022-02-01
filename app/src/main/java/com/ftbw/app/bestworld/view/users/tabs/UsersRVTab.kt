package com.ftbw.app.bestworld.view.users.tabs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.FragmentTabUsersBinding
import com.ftbw.app.bestworld.model.user.UserRecyclerDTO
import com.ftbw.app.bestworld.helper.EventCommon.Companion.COMPANY
import com.ftbw.app.bestworld.helper.EventCommon.Companion.PARTICULAR
import com.ftbw.app.bestworld.view.userprofile.UserProfileFragment
import com.ftbw.app.bestworld.view.users.UsersPresenter
import com.ftbw.app.bestworld.view.users.UsersView
import com.ftbw.app.bestworld.view.users.adapter.recyclerview.RViewUsersAdapter

class UsersRVTab(val listener: IOpenUserProfileFromUsers, var type: String) :
    Fragment(), UsersView {
    private var _bdg: FragmentTabUsersBinding? = null
    private val bdg get() = _bdg!!

    private val presenter by lazy { UsersPresenter(this) }

    lateinit var mContext: Context

    private lateinit var adapter: RViewUsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bdg = FragmentTabUsersBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bdg.search.hint = when (type) {
            PARTICULAR -> getString(R.string.searchUsers)
            COMPANY -> getString(R.string.searchCompanies)
            else -> getString(R.string.somethingWentWrong)
        }

        bdg.loading.root.visibility = View.VISIBLE
        bdg.suchEmpty.root.visibility = View.GONE

        presenter.getUsersByType(type)
    }


    private fun goToUserProfile(it: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, UserProfileFragment(it)).commit()
    }

    override fun showUsers(users: List<UserRecyclerDTO>) {
        bdg.loading.root.visibility = View.GONE
        if (users.isEmpty()) {
            bdg.suchEmpty.root.visibility = View.VISIBLE
        } else {
            bdg.suchEmpty.root.visibility = View.GONE
            bdg.recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = RViewUsersAdapter(
                { listener.openUserProfileFromUsers(it) }, mContext, users
            )
            bdg.recyclerView.adapter = adapter
        }
    }

    override fun somethingWentWrong() {
        Toast.makeText(mContext, getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    interface IOpenUserProfileFromUsers {
        fun openUserProfileFromUsers(userKey: String)
    }
}