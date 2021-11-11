package com.ftbw.app.bestworld.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ftbw.app.bestworld.databinding.FragmentUserProfileBinding
import com.ftbw.app.bestworld.viewmodel.UsersViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserProfileFragment(var userKey: String) : Fragment() {

    private var _bdg: FragmentUserProfileBinding? = null
    private val bdg get() = _bdg!!

    private lateinit var viewModel: UsersViewModel

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
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        if (userKey == Firebase.auth.currentUser!!.uid) {
            Toast.makeText(context, "USER PRINCIPAL", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "USER NO PRINCIPAL", Toast.LENGTH_SHORT).show()
        }

        bdg.salir.setOnClickListener {
            Firebase.auth.signOut()
            // ---> activity?.recreate()
            //Y DEVOLVER AL INICIO
        }

        viewModel.getUser(userKey)
        viewModel.user.observe(viewLifecycleOwner, {
            bdg.name.text = it.name
            bdg.email.text = it.email
            //setUserCredentials
        })

    }

}