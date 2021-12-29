package com.ftbw.app.bestworld.neworden.view.eventfile

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityEventFileBinding
import com.ftbw.app.bestworld.model.event.EventDTO
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.getLabelInSpanish
import com.ftbw.app.bestworld.neworden.helper.Common.Companion.setLabelBackgroundColor

class EventFileActivity : AppCompatActivity(), EventFileView {

    lateinit var bdg: ActivityEventFileBinding

    private val presenter by lazy { EventFilePresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = ActivityEventFileBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        bdg.loading.root.visibility = View.VISIBLE

        val eventLabel: String = intent.getStringExtra("label").toString().lowercase()
        val eventKey: String = intent.getStringExtra("key").toString()

        findViewById<ImageView>(R.id.backButton).setOnClickListener { finish() }

        presenter.getSpecificEvent(eventLabel, eventKey)

        bdg.signUpButton.setOnClickListener {
            bdg.signUpButton.isEnabled = false
            presenter.doSignUp(this, bdg.signUpText.text.toString(), eventLabel, eventKey)
        }
    }

    override fun setEventData(event: EventDTO, userExists: Boolean, isUserSignedUp: Boolean) {
        bdg.label.text = getLabelInSpanish(this, event.label!!)
        bdg.numberOfAssistants.text = event.assistantsCount.toString()
        setLabelBackgroundColor(this, bdg.label.background, event.label)
        bdg.title.text = event.title
        event.imageURL?.let { presenter.setEventImage(it) }
        bdg.description.text = event.description
        event.otherInfo?.let { presenter.setOtherInfo(it) }
        bdg.address.text = event.address
        bdg.date.text = event.date
        bdg.time.text = event.time
        bdg.creator.text = event.creatorName

        if (!userExists) {
            bdg.signUpButton.visibility = View.GONE
        } else {
            setSignUpButton(isUserSignedUp)
            bdg.signUpButton.visibility = View.VISIBLE
        }

        bdg.loading.root.visibility = View.GONE
    }

    override fun showEventImage(exists: Boolean, imageURL: String) {
        when (exists) {
            true -> {
                Glide.with(this).load(imageURL).into(bdg.image)
                bdg.image.visibility = View.VISIBLE
            }
            false -> bdg.image.visibility = View.GONE
        }
    }

    override fun setOtherInfo(view: Int, otherInfo: String) {
        bdg.linearOtherInfo.visibility = view
        bdg.otherInfo.text = otherInfo
    }

    override fun userSignedUpInEvent(assistants: Long, signedUp: Boolean) {
        when (signedUp) {
            true -> {
                bdg.signUpText.text = getString(R.string.signDownToEvent)
                bdg.signUpIcon.setBackgroundResource(R.drawable.ic_users_remove)
            }
            false -> {
                bdg.signUpText.text = getString(R.string.signUpToEvent)
                bdg.signUpIcon.setBackgroundResource(R.drawable.ic_event_assistant)
            }
        }
        bdg.signUpButton.isEnabled = true
        bdg.numberOfAssistants.text = assistants.toString()
    }

    private fun setSignUpButton(isSignedUp: Boolean) {
        when (isSignedUp) {
            true -> {
                bdg.signUpText.text = getString(R.string.signDownToEvent)
                bdg.signUpIcon.setBackgroundResource(R.drawable.ic_users_remove)
            }
            false -> {
                bdg.signUpText.text = getString(R.string.signUpToEvent)
                bdg.signUpIcon.setBackgroundResource(R.drawable.ic_event_assistant)
            }
        }
    }
}