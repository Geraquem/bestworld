package com.ftbw.app.bestworld.view.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityEventFileBinding
import com.ftbw.app.bestworld.helper.EventHelper.Companion.getLabelInSpanish
import com.ftbw.app.bestworld.helper.EventHelper.Companion.setLabelBackgroundColor
import com.ftbw.app.bestworld.model.event.EventDTO
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

        viewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        viewModel.getSpecificEvent(eventLabel, eventKey)
        viewModel.event.observe(this, {
            setEventAttributes(it)
            if (it.label != null) checkIfSignUpButtonShouldExist(it.label, eventKey)
        })

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish();
        }

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

    private fun setEventAttributes(event: EventDTO) {
        bdg.label.text = getLabelInSpanish(this, event.label!!)
        bdg.numberOfAssistants.text = event.assistantsCount.toString()
        setLabelBackgroundColor(this, bdg.label.background, event.label)
        bdg.title.text = event.title
        //set image
        bdg.description.text = event.description
        checkIfOtherInfoExists(event.otherInfo)
        bdg.creator.text = event.creatorName
        bdg.address.text = event.address
        bdg.date.text = event.date
        bdg.time.text = event.time
    }

    private fun checkIfOtherInfoExists(otherInfo: String?) {
        if (otherInfo != null && otherInfo.isBlank() && otherInfo.isEmpty()) {
            bdg.linearOtherInfo.visibility = View.GONE
        } else {
            bdg.linearOtherInfo.visibility = View.VISIBLE
            bdg.otherInfo.text = otherInfo
        }
    }
}