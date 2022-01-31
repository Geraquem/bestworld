package com.ftbw.app.bestworld.view.create.createevent

import android.content.Context
import android.net.Uri
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityCreateEventBinding
import com.ftbw.app.bestworld.helper.Common.Companion.CHOOSE_CATEGORY
import com.ftbw.app.bestworld.model.event.EventDTO
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Month
import java.time.format.TextStyle
import java.util.*
import kotlin.coroutines.CoroutineContext

class CreateEventPresenter(val view: CreateEventView) : CreateEventRepository.ICreateEvent,
    CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val repository by lazy { CreateEventRepository(this) }

    fun spinnerAdapter(context: Context): SpinnerAdapter {
        ArrayAdapter.createFromResource(
            context,
            R.array.event_labels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)
            return adapter
        }
    }

    fun getCreatorOfEvent(userKey: String) {
        launch(Dispatchers.IO) {
            repository.getCreatorOfEvent(userKey)
        }
    }

    fun createEvent(
        context: Context,
        title: String,
        description: String,
        otherInfo: String,
        address: String,
        label: String,
        dateText: String,
        timeText: String,
        userName: String,
        imageUri: Uri?
    ) {
        if (!checkFields(context, title, description, address, label, dateText, timeText)) {
            val event = EventDTO(
                "", label, title,
                description,
                otherInfo,
                address,
                "",
                userName,
                Firebase.auth.currentUser!!.uid,
                dateText,
                timeText
            )
            launch(Dispatchers.IO) {
                repository.createEvent(event, imageUri)
            }
        }
    }

    private fun checkFields(
        context: Context,
        title: String,
        description: String,
        address: String,
        label: String,
        date: String,
        time: String
    ): Boolean {
        if (title.isEmpty()) {
            view.setErrorMessage(context.getString(R.string.errorMessageEventTitle))
            return true
        }
        if (description.isEmpty()) {
            view.setErrorMessage(context.getString(R.string.errorMessageEventDescription))
            return true
        }
        if (address.isEmpty()) {
            view.setErrorMessage(context.getString(R.string.errorMessageEventAddress))
            return true
        }
        if (label == CHOOSE_CATEGORY) {
            view.setErrorMessage(context.getString(R.string.errorMessageEventCategory))
            return true
        }
        if (date.isEmpty() || date.isBlank()) {
            view.setErrorMessage(context.getString(R.string.errorMessageEventDate))
            return true
        }
        if (time.isEmpty() || time.isBlank()) {
            view.setErrorMessage(context.getString(R.string.errorMessageEventTime))
            return true
        }
        return false
    }

    fun onDateSelected(bdg: ActivityCreateEventBinding, day: Int, month: Int, year: Int) {
        val monthName = getMonthNameByNumber(month).replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        val completeDay = checkIfTimeHasOnlyOneNumber(day.toString())
        val date = "$completeDay $monthName $year"
        bdg.dateText.text = date
    }

    fun onTimeSelected(bdg: ActivityCreateEventBinding, hour: Int, minutes: Int) {
        val completedHour = checkIfTimeHasOnlyOneNumber(hour.toString())
        val completedMinute = checkIfTimeHasOnlyOneNumber(minutes.toString())
        val time = "$completedHour:$completedMinute"
        bdg.timeText.text = time
    }

    private fun getMonthNameByNumber(number: Int): String {
        return Month.of(number + 1)
            .getDisplayName(TextStyle.FULL_STANDALONE, Locale("es", "ES"))
    }

    private fun checkIfTimeHasOnlyOneNumber(time: String): String {
        if (time.length == 1) {
            return "0$time"
        }
        return time
    }

    override fun creatorOfEvent(name: String) {
        launch { view.creatorOfEvent(name) }
    }

    override fun eventCreated() {
        launch { view.eventCreated() }
    }

    override fun somethingWentWrong() {
        launch { view.somethingWentWrong() }
    }
}