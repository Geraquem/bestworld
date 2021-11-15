package com.ftbw.app.bestworld.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityCreateEventBinding
import com.ftbw.app.bestworld.helper.BottomNavHelper.Companion.CREATE_EVENT_ACTIVITY_REQUEST_CODE
import com.ftbw.app.bestworld.helper.EventHelper.Companion.getLabel
import com.ftbw.app.bestworld.helper.EventHelper.Companion.isThereFailures
import com.ftbw.app.bestworld.helper.EventHelper.Companion.setErrorMessage
import com.ftbw.app.bestworld.model.event.EventDTO
import com.ftbw.app.bestworld.viewmodel.EventsViewModel
import com.ftbw.app.bestworld.viewmodel.UsersViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateEventActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var bdg: ActivityCreateEventBinding

    private lateinit var eventsViewModel: EventsViewModel
    private lateinit var usersViewModel: UsersViewModel

    private var label: String = ""
    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)
        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish();
        }
        findViewById<TextView>(R.id.toolbarText).text = getString(R.string.createEventTitle)

        ArrayAdapter.createFromResource(
            this,
            R.array.event_labels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bdg.spinner.adapter = adapter
        }
        bdg.spinner.onItemSelectedListener = this

        usersViewModel.getUser(Firebase.auth.currentUser!!.uid)
        usersViewModel.user.observe(this, {
            checkIfUserIsAvailable(it.name)
        })

        eventsViewModel.isEventSaved.observe(this, {
            if (it) {
                setResult(CREATE_EVENT_ACTIVITY_REQUEST_CODE, Intent())
                finish()
            } else {
                setErrorMessage(bdg, getString(R.string.somethingWentWrong))
            }
        })

        bdg.errorMessage.visibility = View.GONE
        bdg.loading.visibility = View.GONE
        bdg.createButton.isEnabled = true
        bdg.uploadImage.setOnClickListener {
            System.out.println(" ---------------------> userName: " + userName)
        }

        bdg.createButton.setOnClickListener {
            doOnclick()
        }
    }

    private fun doOnclick() {
        closeKeyboard()
        bdg.errorMessage.visibility = View.GONE

        val title = bdg.title.text.toString()
        val description = bdg.description.text.toString()
        val address = bdg.address.text.toString()
        val otherInfo = bdg.otherInfo.text.toString()

        if (!isThereFailures(this, bdg, title, description, address, label)) {
            val event = EventDTO(
                "",
                label,
                title,
                description,
                otherInfo,
                address,
                "",
                userName,
                Firebase.auth.currentUser!!.uid
            )
            eventsViewModel.saveEvent(event)
        }
    }

    private fun closeKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun checkIfUserIsAvailable(name: String) {
        if (name == "" || name.isBlank() || name.isEmpty()) {
            bdg.loading.visibility = View.VISIBLE
            bdg.createButton.isEnabled = false
        } else {
            bdg.loading.visibility = View.GONE
            bdg.createButton.isEnabled = true
            userName = name
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        this.label = getLabel(this, p0!!.getItemAtPosition(p2).toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}