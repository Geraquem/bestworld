package com.ftbw.app.bestworld.view.fragments

import android.os.Bundle
import android.os.TokenWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.databinding.FragmentEventsBinding
import com.ftbw.app.bestworld.databinding.FragmentUserProfileBinding
import com.google.android.gms.common.FirstPartyScopes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserProfileFragment(var userKey: String) : Fragment() {

    private var _bdg: FragmentUserProfileBinding? = null
    private val bdg get() = _bdg!!

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

        // Si user.userId == FirebaseAuth.getInstance().currentUser
            //Estoy en mi perfil,
            // else -> perfil de otra persona

        if(userKey == Firebase.auth.currentUser!!.uid){
            Toast.makeText(context, "USER PRINCIPAL", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "USER NO PRINCIPAL", Toast.LENGTH_SHORT).show()
        }

        bdg.salir.setOnClickListener{
            Firebase.auth.signOut()

            // ---> activity?.recreate()
            //Y DEVOLVER AL INICIO
        }
    }

}