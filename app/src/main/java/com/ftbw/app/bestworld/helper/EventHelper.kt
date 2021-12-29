package com.ftbw.app.bestworld.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat.getColor
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityCreateEventBinding
import java.time.Month
import java.time.format.TextStyle
import java.util.*

class EventHelper {
    companion object {

        fun setErrorMessage(
            bdg: ActivityCreateEventBinding,
            message: String
        ) {
            bdg.loadingCreating.visibility = View.GONE
            bdg.errorMessage.visibility = View.VISIBLE
            bdg.errorMessage.text = message
            bdg.createButton.isEnabled = true
        }

        private fun checkIfTimeHasOnlyOneNumber(time: String): String {
            if (time.length == 1) {
                return "0$time"
            }
            return time
        }

        private fun getMonthNameByNumber(number: Int): String {
            return Month.of(number + 1)
                .getDisplayName(TextStyle.FULL_STANDALONE, Locale("es", "ES"))
        }

        private fun setImageEvent(context: Context, imageURL: String, imageView: ImageView) {
            if (imageURL == "" || imageURL.isEmpty() || imageURL.isBlank()) {
                imageView.setBackgroundResource(R.drawable.bw_logo)
            } else {
                Glide.with(context).load(imageURL).into(imageView)
            }
        }


    }
}