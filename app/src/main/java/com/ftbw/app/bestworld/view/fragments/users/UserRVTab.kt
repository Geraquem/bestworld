package com.ftbw.app.bestworld.view.fragments.users

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.adapter.recyclerview.RViewUsersAdapter
import com.ftbw.app.bestworld.databinding.FragmentTabUsersBinding
import com.ftbw.app.bestworld.model.user.UserRecyclerDTO
import com.ftbw.app.bestworld.viewmodel.UsersViewModel

class UserRVTab(var type: String) : Fragment() {
    private var _bdg: FragmentTabUsersBinding? = null
    private val bdg get() = _bdg!!

    lateinit var getContext: Context

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

        if (type == "particular") {
            bdg.search.hint = getString(R.string.searchUsers)
        } else {
            bdg.search.hint = getString(R.string.searchCompanies)
        }

        bdg.loading.visibility = View.VISIBLE

        viewModel.getUsersByType(type)
        viewModel.listUsers.observe(viewLifecycleOwner, {
            bdg.loading.visibility = View.GONE
            if (it.isEmpty()) {
                bdg.recyclerView.visibility = View.GONE
                bdg.suchEmpty.visibility = View.VISIBLE
            } else {
                initRecyclerView(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun initRecyclerView(list: List<UserRecyclerDTO>) {
        bdg.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RViewUsersAdapter(requireContext(), list)
        bdg.recyclerView.adapter = adapter

        bdg.recyclerView.visibility = View.VISIBLE
        bdg.suchEmpty.visibility = View.GONE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getContext = context
    }
}