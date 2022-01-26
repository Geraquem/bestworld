package com.ftbw.app.bestworld.view.userprofile.tabs

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserProfileRVTabPresenter(var view: UserProfileRVTabView) :
    UserProfileRVTabRepository.IUserProfileRVTab, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

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
        launch(Dispatchers.IO) {
            repository.getEventsRelatedWithUser(relation, userKey, eventLabel)
        }
    }

    override fun events(events: List<EventRecyclerDTO>) {
        launch {
            view.setEvents(events)
        }
    }

    override fun somethingWentWrong() {
        launch {
            view.somethingWentWrong()
        }
    }
}