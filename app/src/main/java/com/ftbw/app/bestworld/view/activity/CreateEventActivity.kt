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
import com.ftbw.app.bestworld.helper.EventHelper.Companion.checkIfTimeHasOnlyOneNumber
import com.ftbw.app.bestworld.helper.EventHelper.Companion.getLabelInEnglish
import com.ftbw.app.bestworld.helper.EventHelper.Companion.getMonthNameByNumber
import com.ftbw.app.bestworld.helper.EventHelper.Companion.isThereFailures
import com.ftbw.app.bestworld.helper.EventHelper.Companion.setErrorMessage
import com.ftbw.app.bestworld.model.event.EventDTO
import com.ftbw.app.bestworld.view.picker.DatePickerFragment
import com.ftbw.app.bestworld.view.picker.TimePickerFragment
import com.ftbw.app.bestworld.viewmodel.EventsViewModel
import com.ftbw.app.bestworld.viewmodel.UsersViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

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
            adapter.setDropDownViewResource(R.layout.spinner_item)
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

        bdg.selectDate.setOnClickListener {
            DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
                .show(supportFragmentManager, "datePicker")
        }

        bdg.selectTime.setOnClickListener {
            TimePickerFragment { hour, minutes -> onTimeSelected(hour, minutes) }
                .show(supportFragmentManager, "timePicker")
        }

        bdg.uploadImage.setOnClickListener {
            //UPLOAD IMAGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
        }

        bdg.createButton.setOnClickListener {
            doOnclick()
        }
    }

    private fun onTimeSelected(hour: Int, minutes: Int) {
        val completedHour = checkIfTimeHasOnlyOneNumber(hour.toString())
        val completedMinute = checkIfTimeHasOnlyOneNumber(minutes.toString())
        val time = "$completedHour:$completedMinute"
        bdg.timeText.text = time
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val monthName = getMonthNameByNumber(month).replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        val completeDay = checkIfTimeHasOnlyOneNumber(day.toString())
        val date = "$completeDay-$monthName-$year"
        bdg.dateText.text = date
    }


    private fun doOnclick() {
        closeKeyboard()
        bdg.errorMessage.visibility = View.GONE

        val title = bdg.title.text.toString()
        val description = bdg.description.text.toString()
        val otherInfo = bdg.otherInfo.text.toString()
        val address = bdg.address.text.toString()
        val date = bdg.dateText.text.toString()
        val time = bdg.timeText.text.toString()

        if (!isThereFailures(this, bdg, title, description, address, label, date, time)) {
            val event = EventDTO(
                "",
                label,
                title,
                description,
                otherInfo,
                address,
                "",
                userName,
                Firebase.auth.currentUser!!.uid,
                date,
                time
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
        this.label = getLabelInEnglish(this, p0!!.getItemAtPosition(p2).toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}