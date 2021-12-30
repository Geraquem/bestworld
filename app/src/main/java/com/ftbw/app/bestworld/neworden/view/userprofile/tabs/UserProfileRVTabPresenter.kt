package com.ftbw.app.bestworld.neworden.view.userprofile.tabs

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO

class UserProfileRVTabPresenter(var view: UserProfileRVTabView) :
    UserProfileRVTabRepository.IUserProfileRVTab {

    private val repository by lazy { UserProfileRVTabRepository(this) }

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

    fun getEventsRelatedWithUser(relation: String, userKey: String, eventLabel: String) {
        repository.getEventsRelatedWithUser(relation, userKey, eventLabel)
    }

    override fun events(events: List<EventRecyclerDTO>) {
        view.setEvents(events)
    }

    override fun somethingWentWrong() {
        view.somethingWentWrong()
    }
}