package com.ftbw.app.bestworld.view.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityEventFileBinding
import com.ftbw.app.bestworld.helper.EventFileHelper.setEventAttributes
import com.ftbw.app.bestworld.viewmodel.EventsViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EventFileActivity : AppCompatActivity() {

    lateinit var bdg: ActivityEventFileBinding

    private lateinit var viewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = ActivityEventFileBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        bdg.loading.root.visibility = View.VISIBLE

        val eventLabel: String = intent.getStringExtra("label").toString().lowercase()
        val eventKey: String = intent.getStringExtra("key").toString()

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish();
        }

        viewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        viewModel.getSpecificEvent(eventLabel, eventKey)
        viewModel.event.observe(this, {
            setEventAttributes(this, bdg, it)
            if (it.label != null) checkIfSignUpButtonShouldExist(it.label, eventKey)
        })

        viewModel.isUserAlreadySignedUp.observe(this, {
            if (it) {
                bdg.signUpText.text = getString(R.string.signDownToEvent)
                bdg.signUpIcon.setBackgroundResource(R.drawable.ic_users_remove)
            } else {
                bdg.signUpText.text = getString(R.string.signUpToEvent)
                bdg.signUpIcon.setBackgroundResource(R.drawable.ic_event_assistant)
            }
            bdg.loading.root.visibility = View.GONE
        })

        bdg.signUpButton.setOnClickListener {
            bdg.signUpButton.isEnabled = false
            doSignUp(eventLabel, eventKey)
        }

        viewModel.isUserGoingToAssist.observe(this, {
            if (it) { //signedUp
                bdg.signUpText.text = getString(R.string.signDownToEvent)
                bdg.signUpIcon.setBackgroundResource(R.drawable.ic_users_remove)
            } else { //signedDown
                bdg.signUpText.text = getString(R.string.signUpToEvent)
                bdg.signUpIcon.setBackgroundResource(R.drawable.ic_event_assistant)
            }
            viewModel.updateAssistantCount(eventLabel, eventKey)
            bdg.signUpButton.isEnabled = true
        })

        viewModel.numberOfAssistants.observe(this, {
            bdg.numberOfAssistants.text = it.toString()
        })
    }

    private fun doSignUp(label: String, eventKey: String) {
        val userKey = Firebase.auth.currentUser!!.uid
        if (bdg.signUpText.text == getString(R.string.signUpToEvent)) {
            viewModel.userSignUpInEvent(userKey, eventKey, label, true)
        } else if (bdg.signUpText.text == getString(R.string.signDownToEvent)) {
            viewModel.userSignUpInEvent(userKey, eventKey, label, false)
        }
    }

    private fun checkIfSignUpButtonShouldExist(label: String, eventKey: String) {
        if (Firebase.auth.currentUser == null) {
            bdg.signUpButton.visibility = View.GONE
            bdg.loading.root.visibility = View.GONE
        } else {
            viewModel.checkIfUserIsSignedUp(Firebase.auth.currentUser!!.uid, eventKey, label)
        }
    }
}