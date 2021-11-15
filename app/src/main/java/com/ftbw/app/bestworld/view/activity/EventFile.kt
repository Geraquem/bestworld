package com.ftbw.app.bestworld.view.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityEventFileBinding
import com.ftbw.app.bestworld.model.event.EventDTO
import com.ftbw.app.bestworld.viewmodel.EventsViewModel

class EventFile : AppCompatActivity() {

    lateinit var bdg: ActivityEventFileBinding

    private lateinit var viewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = ActivityEventFileBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        val eventLabel: String = intent.getStringExtra("label").toString().lowercase()
        val key: String = intent.getStringExtra("key").toString()

        viewModel = ViewModelProvider(this).get(EventsViewModel::class.java)
        viewModel.getSpecificEvent(eventLabel, key)
        viewModel.event.observe(this, {
            setEventAttributes(it)
        })

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish();
        }
    }

    private fun setEventAttributes(event: EventDTO) {
        bdg.title.text = event.title
        bdg.description.text = event.description
        bdg.creator.text = event.creatorName
    }
}