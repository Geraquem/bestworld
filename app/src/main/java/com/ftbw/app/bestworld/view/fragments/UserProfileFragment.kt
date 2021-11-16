package com.ftbw.app.bestworld.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.FragmentUserProfileBinding
import com.ftbw.app.bestworld.viewmodel.EventsViewModel
import com.ftbw.app.bestworld.viewmodel.UsersViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserProfileFragment(var userKey: String) : Fragment(), AdapterView.OnItemSelectedListener {

    private var _bdg: FragmentUserProfileBinding? = null
    private val bdg get() = _bdg!!

    lateinit var getContext: Context

    private lateinit var usersViewModel: UsersViewModel
    private lateinit var eventsViewModel: EventsViewModel

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
        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        ArrayAdapter.createFromResource(
            getContext,
            R.array.event_labels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)
            bdg.spinner.adapter = adapter
        }
        bdg.spinner.onItemSelectedListener = this

        bdg.salir.setOnClickListener {
            Firebase.auth.signOut()
            // ---> activity?.recreate()
            //Y DEVOLVER AL INICIO
        }

        usersViewModel.getUser(userKey)
        usersViewModel.user.observe(viewLifecycleOwner, {
            bdg.name.text = it.name
            bdg.email.text = it.email
            //setUserCredentials
        })

        eventsViewModel.listCreatedEvents.observe(viewLifecycleOwner, {
            bdg.loadingEvents.visibility = View.GONE

        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getContext = context
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val eventLabel = p0!!.getItemAtPosition(p2).toString()
        if (eventLabel != "Selecciona categoría") {
            bdg.loadingEvents.visibility = View.VISIBLE
            eventsViewModel.getCreatedEventsByUser(userKey, eventLabel)
        } else {
            bdg.loadingEvents.visibility = View.GONE
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}