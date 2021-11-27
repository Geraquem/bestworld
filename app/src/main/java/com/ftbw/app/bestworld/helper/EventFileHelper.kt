package com.ftbw.app.bestworld.helper

import android.view.View
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.databinding.ActivityEventFileBinding
import com.ftbw.app.bestworld.helper.EventHelper.Companion.setLabelBackgroundColor
import com.ftbw.app.bestworld.model.event.EventDTO
import com.ftbw.app.bestworld.view.activity.EventFileActivity

object EventFileHelper {

    fun setEventAttributes(
        context: EventFileActivity,
        bdg: ActivityEventFileBinding,
        event: EventDTO
    ) {
        bdg.label.text = EventHelper.getLabelInSpanish(context, event.label!!)
        bdg.numberOfAssistants.text = event.assistantsCount.toString()
        setLabelBackgroundColor(context, bdg.label.background, event.label)
        bdg.title.text = event.title
        setImageByUrl(context, bdg, event.imageURL)
        bdg.description.text = event.description
        checkIfOtherInfoExists(bdg, event.otherInfo)
        bdg.address.text = event.address
        bdg.date.text = event.date
        bdg.time.text = event.time
        bdg.creator.text = event.creatorName
    }

    private fun setImageByUrl(
        context: EventFileActivity,
        bdg: ActivityEventFileBinding,
        imageURL: String?
    ) {
        if (imageURL == "") {
            bdg.image.visibility = View.GONE
        } else {
            Glide.with(context).load(imageURL).into(bdg.image)
            bdg.image.visibility = View.VISIBLE
        }
    }

    private fun checkIfOtherInfoExists(bdg: ActivityEventFileBinding, otherInfo: String?) {
        if (otherInfo != null && otherInfo.isBlank() && otherInfo.isEmpty()) {
            bdg.linearOtherInfo.visibility = View.GONE
        } else {
            bdg.linearOtherInfo.visibility = View.VISIBLE
            bdg.otherInfo.text = otherInfo
        }
    }
}