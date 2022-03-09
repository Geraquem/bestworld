package com.ftbw.app.bestworld.helper

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.ftbw.app.bestworld.view.eventfile.EventFileActivity

class EventCommon {

    companion object {

        const val LOGIN_ACTIVITY_REQUEST_CODE = 1
        const val REGISTER_ACTIVITY_REQUEST_CODE = 2
        const val CREATE_EVENT_ACTIVITY_REQUEST_CODE = 3

        const val COMPANY = "company"
        const val PARTICULAR = "particular"

        const val CREATED_EVENTS = "createdEvents"
        const val ASSISTANT_EVENTS = "assistantEvents"

        const val CHOOSE_CATEGORY = "Selecciona categoría"
        const val ALL_EVENTS = "allEvents"
        const val ENVIRONMENTAL = "environmental"
        const val DIVULGATION = "divulgation"
        const val FARMING = "farming"
        const val MOBILIZATION = "mobilization"
        const val WORKSHOP = "workshop"
        const val SHARING_CAR = "sharingCar"
        const val OTHER = "other"

        fun getLabelInEnglish(context: Context, labelSelected: String): String {
            return when (labelSelected) {
                context.getString(R.string.allEvents) -> ALL_EVENTS
                context.getString(R.string.EnvironmentalTitleTab) -> ENVIRONMENTAL
                context.getString(R.string.DivulgationTitleTab) -> DIVULGATION
                context.getString(R.string.FarmingTitleTab) -> FARMING
                context.getString(R.string.MobilizationTitleTab) -> MOBILIZATION
                context.getString(R.string.WorkshopTitleTab) -> WORKSHOP
                context.getString(R.string.SharingCarTitleTab) -> SHARING_CAR
                context.getString(R.string.OtherTitleTab) -> OTHER
                else -> CHOOSE_CATEGORY
            }
        }

        fun getLabelInSpanish(context: Context, labelSelected: String): String {
            return when (labelSelected) {
                ALL_EVENTS -> context.getString(R.string.allEvents)
                ENVIRONMENTAL -> context.getString(R.string.EnvironmentalTitleTab)
                DIVULGATION -> context.getString(R.string.DivulgationTitleTab)
                FARMING -> context.getString(R.string.FarmingTitleTab)
                MOBILIZATION -> context.getString(R.string.MobilizationTitleTab)
                WORKSHOP -> context.getString(R.string.WorkshopTitleTab)
                SHARING_CAR -> context.getString(R.string.SharingCarTitleTab)
                OTHER -> context.getString(R.string.OtherTitleTab)
                else -> context.getString(R.string.somethingWentWrong)
            }
        }

        fun setLabelBackgroundColor(context: Context, background: Drawable, label: String) {
            when (label) {
                ENVIRONMENTAL -> setColor(
                    background, ContextCompat.getColor(context, R.color.Enviromental)
                )
                DIVULGATION -> setColor(
                    background, ContextCompat.getColor(context, R.color.Divulgation)
                )
                FARMING -> setColor(
                    background, ContextCompat.getColor(context, R.color.Farming)
                )
                MOBILIZATION -> setColor(
                    background, ContextCompat.getColor(context, R.color.Mobilization)
                )
                WORKSHOP -> setColor(
                    background, ContextCompat.getColor(context, R.color.Workshop)
                )
                OTHER -> setColor(
                    background, ContextCompat.getColor(context, R.color.Other)
                )
                else -> setColor(
                    background, ContextCompat.getColor(context, R.color.Other)
                )
            }
        }

        private fun setColor(background: Drawable, color: Int) {
            background as GradientDrawable
            background.setColor(color)
        }

        fun setEventImage(context: Context, imageURL: String, imageView: ImageView) {
            if (imageURL == "" || imageURL.isEmpty() || imageURL.isBlank()) {
                imageView.setBackgroundResource(R.drawable.bw_logo)
            } else {
                Glide.with(context).load(imageURL).into(imageView)
            }
        }

        fun goToEventFile(context: Context, event: EventRecyclerDTO) {
            context.startActivity(Intent(context, EventFileActivity::class.java).apply {
                putExtra("key", event.key)
                putExtra("label", event.label)
            })
        }
    }
}