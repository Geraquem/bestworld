package com.ftbw.app.bestworld.view.create.createevent.picker

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment(val listener: (hour: Int, minutes: Int) -> Unit) :
    DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        listener(p1, p2)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(activity as Context, this, hour, minute, true)
    }
}