package com.ftbw.app.bestworld.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat.getColor
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityCreateEventBinding
import java.time.Month
import java.time.format.TextStyle
import java.util.*

class EventHelper {
    companion object {

        private const val CHOOSE_CATEGORY = "Selecciona categoría"
        const val ENVIRONMENTAL = "environmental"
        const val DIVULGATION = "divulgation"
        const val FARMING = "farming"
        const val MOBILIZATION = "mobilization"
        const val WORKSHOP = "workshop"
        const val OTHER = "other"

        fun isThereFailures(
            context: Context,
            bdg: ActivityCreateEventBinding,
            title: String,
            description: String,
            address: String,
            label: String,
            date: String,
            time: String
        ): Boolean {
            if (title.isEmpty()) {
                setErrorMessage(bdg, context.getString(R.string.errorMessageEventTitle))
                return true
            }
            if (description.isEmpty()) {
                setErrorMessage(bdg, context.getString(R.string.errorMessageEventDescription))
                return true
            }
            if (address.isEmpty()) {
                setErrorMessage(bdg, context.getString(R.string.errorMessageEventAddress))
                return true
            }
            if (label == "Selecciona categoría") {
                setErrorMessage(bdg, context.getString(R.string.errorMessageEventCategory))
                return true
            }
            if (date.isEmpty() || date.isBlank()) {
                setErrorMessage(bdg, context.getString(R.string.errorMessageEventDate))
                return true
            }
            if (time.isEmpty() || time.isBlank()) {
                setErrorMessage(bdg, context.getString(R.string.errorMessageEventTime))
                return true
            }
            return false
        }

        fun setErrorMessage(
            bdg: ActivityCreateEventBinding,
            message: String
        ) {
            bdg.loading.visibility = View.GONE
            bdg.errorMessage.visibility = View.VISIBLE
            bdg.errorMessage.text = message
            bdg.createButton.isEnabled = true
        }

        fun getLabelInEnglish(context: Context, labelSelected: String): String {
            return when (labelSelected) {
                context.getString(R.string.EnvironmentalTitleTab) -> ENVIRONMENTAL
                context.getString(R.string.DivulgationTitleTab) -> DIVULGATION
                context.getString(R.string.FarmingTitleTab) -> FARMING
                context.getString(R.string.MobilizationTitleTab) -> MOBILIZATION
                context.getString(R.string.WorkshopTitleTab) -> WORKSHOP
                context.getString(R.string.OtherTitleTab) -> OTHER
                else -> CHOOSE_CATEGORY
            }
        }

        fun getLabelInSpanish(context: Context, labelSelected: String): String {
            return when (labelSelected) {
                ENVIRONMENTAL -> context.getString(R.string.EnvironmentalTitleTab)
                DIVULGATION -> context.getString(R.string.DivulgationTitleTab)
                FARMING -> context.getString(R.string.FarmingTitleTab)
                MOBILIZATION -> context.getString(R.string.MobilizationTitleTab)
                WORKSHOP -> context.getString(R.string.WorkshopTitleTab)
                OTHER -> context.getString(R.string.OtherTitleTab)
                else -> context.getString(R.string.somethingWentWrong)
            }
        }

        fun setLabelBackgroundColor(context: Context, background: Drawable, label: String) {
            when (label) {
                ENVIRONMENTAL -> setColor(background, getColor(context, R.color.Enviromental))
                DIVULGATION -> setColor(background, getColor(context, R.color.Divulgation))
                FARMING -> setColor(background, getColor(context, R.color.Farming))
                MOBILIZATION -> setColor(background, getColor(context, R.color.Mobilization))
                WORKSHOP -> setColor(background, getColor(context, R.color.Workshop))
                OTHER -> setColor(background, getColor(context, R.color.Other))
                else -> setColor(background, getColor(context, R.color.Other))
            }
        }

        private fun setColor(background: Drawable, color: Int) {
            background as GradientDrawable
            background.setColor(color)
        }

        fun checkIfTimeHasOnlyOneNumber(time: String): String {
            if (time.length == 1) {
                return "0$time"
            }
            return time
        }

        fun getMonthNameByNumber(number: Int): String {
            return Month.of(number + 1)
                .getDisplayName(TextStyle.FULL_STANDALONE, Locale("es", "ES"));
        }
    }
}