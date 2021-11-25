package com.ftbw.app.bestworld.helper

import android.view.View
import com.ftbw.app.bestworld.databinding.ActivityCreateEventBinding
import java.util.*

object CreateEventHelper {

    fun checkIfUserIsAvailable(bdg: ActivityCreateEventBinding, name: String): String {
        return if (name == "" || name.isBlank() || name.isEmpty()) {
            ""
        } else {
            bdg.loading.root.visibility = View.GONE
            name
        }
    }

    fun onTimeSelected(bdg: ActivityCreateEventBinding, hour: Int, minutes: Int) {
        val completedHour = EventHelper.checkIfTimeHasOnlyOneNumber(hour.toString())
        val completedMinute = EventHelper.checkIfTimeHasOnlyOneNumber(minutes.toString())
        val time = "$completedHour:$completedMinute"
        bdg.timeText.text = time
    }

    fun onDateSelected(bdg: ActivityCreateEventBinding, day: Int, month: Int, year: Int) {
        val monthName = EventHelper.getMonthNameByNumber(month).replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        val completeDay = EventHelper.checkIfTimeHasOnlyOneNumber(day.toString())
        val date = "$completeDay $monthName $year"
        bdg.dateText.text = date
    }
}