package com.ftbw.app.bestworld.helper

import android.content.Context
import android.view.View
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityCreateEventBinding

class EventHelper {
    companion object {
        fun isThereFailures(
            context: Context,
            bdg: ActivityCreateEventBinding,
            title: String,
            description: String,
            address: String,
            label: String,
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

        fun getLabel(context: Context, labelSelected: String): String {
            return when (labelSelected) {
                context.getString(R.string.EnvironmentalTitleTab) -> "environmental"
                context.getString(R.string.DivulgationTitleTab) -> "divulgation"
                context.getString(R.string.FarmingTitleTab) -> "farming"
                context.getString(R.string.MobilizationTitleTab) -> "mobilization"
                context.getString(R.string.WorkshopTitleTab) -> "workshop"
                context.getString(R.string.OtherTitleTab) -> "other"
                else -> "Selecciona categoría"
            }
        }
    }
}