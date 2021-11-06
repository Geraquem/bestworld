package com.ftbw.app.bestworld.view.events

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.FragmentTabEventBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EnvironmentalTab : Fragment() {
    private var _bdg: FragmentTabEventBinding? = null
    private val bdg get() = _bdg!!


    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bdg = FragmentTabEventBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bdg.eventTabTitle.text = getString(R.string.EnviromentalTitleTab)


        database = Firebase.database.reference

        database.child("test").child("value").get().addOnSuccessListener {
            System.out.println("-------YES")
            Log.i("firebase", "Got value ${it.value}")
            bdg.text.text = it.value.toString()
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
            System.out.println("-------NOPE")
        }

        database.child("test").child("value").setValue("yeyeyeyeyey")
    }
}