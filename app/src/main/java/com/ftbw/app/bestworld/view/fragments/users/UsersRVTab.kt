package com.ftbw.app.bestworld.view.fragments.users

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.adapter.recyclerview.RViewUsersAdapter
import com.ftbw.app.bestworld.databinding.FragmentTabUsersBinding
import com.ftbw.app.bestworld.model.user.UserRecyclerDTO
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.COMPANY
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.PARTICULAR
import com.ftbw.app.bestworld.neworden.view.userprofile.UserProfileFragment
import com.ftbw.app.bestworld.viewmodel.UsersViewModel

class UsersRVTab(var type: String) : Fragment() {
    private var _bdg: FragmentTabUsersBinding? = null
    private val bdg get() = _bdg!!

    lateinit var mContext: Context

    private lateinit var viewModel: UsersViewModel

    private lateinit var adapter: RViewUsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bdg = FragmentTabUsersBinding.inflate(inflater, container, false)
        return bdg.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        if (type == PARTICULAR) {
            bdg.search.hint = getString(R.string.searchUsers)
        } else if (type == COMPANY) {
            bdg.search.hint = getString(R.string.searchCompanies)
        } else {
            Toast.makeText(mContext, getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT)
                .show()
        }

        bdg.loading.root.visibility = View.VISIBLE
        bdg.suchEmpty.root.visibility = View.GONE

        viewModel.getUsersByType(type)
        viewModel.listUsers.observe(viewLifecycleOwner, {
            bdg.loading.root.visibility = View.GONE
            if (it.isEmpty()) {
                bdg.suchEmpty.root.visibility = View.VISIBLE
            } else {
                initRecyclerView(it)
                adapter.notifyDataSetChanged()
                bdg.suchEmpty.root.visibility = View.GONE
            }
        })
    }

    private fun initRecyclerView(list: List<UserRecyclerDTO>) {
        bdg.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RViewUsersAdapter(
            { goToUserProfile(it) }, mContext, list
        )
        bdg.recyclerView.adapter = adapter
    }

    private fun goToUserProfile(it: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, UserProfileFragment(it)).commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}