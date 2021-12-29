package com.ftbw.app.bestworld.neworden.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.helper.EventHelper

class Common {

    companion object {

        const val LOGIN_ACTIVITY_REQUEST_CODE = 1
        const val REGISTER_ACTIVITY_REQUEST_CODE = 2
        const val CREATE_EVENT_ACTIVITY_REQUEST_CODE = 3

        const val COMPANY = "company"
        const val PARTICULAR = "particular"

        const val CREATED_EVENTS = "createdEvents"
        const val ASSISTANT_EVENTS = "assistantEvents"

        fun getLabelInEnglish(context: Context, labelSelected: String): String {
            return when (labelSelected) {
                context.getString(R.string.EnvironmentalTitleTab) -> EventHelper.ENVIRONMENTAL
                context.getString(R.string.DivulgationTitleTab) -> EventHelper.DIVULGATION
                context.getString(R.string.FarmingTitleTab) -> EventHelper.FARMING
                context.getString(R.string.MobilizationTitleTab) -> EventHelper.MOBILIZATION
                context.getString(R.string.WorkshopTitleTab) -> EventHelper.WORKSHOP
                context.getString(R.string.OtherTitleTab) -> EventHelper.OTHER
                else -> EventHelper.CHOOSE_CATEGORY
            }
        }

        fun getLabelInSpanish(context: Context, labelSelected: String): String {
            return when (labelSelected) {
                EventHelper.ALLEVENTS -> context.getString(R.string.allEvents)
                EventHelper.ENVIRONMENTAL -> context.getString(R.string.EnvironmentalTitleTab)
                EventHelper.DIVULGATION -> context.getString(R.string.DivulgationTitleTab)
                EventHelper.FARMING -> context.getString(R.string.FarmingTitleTab)
                EventHelper.MOBILIZATION -> context.getString(R.string.MobilizationTitleTab)
                EventHelper.WORKSHOP -> context.getString(R.string.WorkshopTitleTab)
                EventHelper.OTHER -> context.getString(R.string.OtherTitleTab)
                else -> context.getString(R.string.somethingWentWrong)
            }
        }

        fun setLabelBackgroundColor(context: Context, background: Drawable, label: String) {
            when (label) {
                EventHelper.ENVIRONMENTAL -> setColor(
                    background,
                    ContextCompat.getColor(context, R.color.Enviromental)
                )
                EventHelper.DIVULGATION -> setColor(
                    background,
                    ContextCompat.getColor(context, R.color.Divulgation)
                )
                EventHelper.FARMING -> setColor(
                    background,
                    ContextCompat.getColor(context, R.color.Farming)
                )
                EventHelper.MOBILIZATION -> setColor(
                    background,
                    ContextCompat.getColor(context, R.color.Mobilization)
                )
                EventHelper.WORKSHOP -> setColor(
                    background,
                    ContextCompat.getColor(context, R.color.Workshop)
                )
                EventHelper.OTHER -> setColor(
                    background,
                    ContextCompat.getColor(context, R.color.Other)
                )
                else -> setColor(
                    background,
                    ContextCompat.getColor(context, R.color.Other)
                )
            }
        }

        private fun setColor(background: Drawable, color: Int) {
            background as GradientDrawable
            background.setColor(color)
        }
    }
}