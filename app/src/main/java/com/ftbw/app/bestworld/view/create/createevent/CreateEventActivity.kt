package com.ftbw.app.bestworld.view.create.createevent

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityCreateEventBinding
import com.ftbw.app.bestworld.helper.EventCommon.Companion.CREATE_EVENT_ACTIVITY_REQUEST_CODE
import com.ftbw.app.bestworld.helper.EventCommon.Companion.getLabelInEnglish
import com.ftbw.app.bestworld.helper.ImagePickerHelper.selectImageFromGallery
import com.ftbw.app.bestworld.view.create.createevent.picker.DatePickerFragment
import com.ftbw.app.bestworld.view.create.createevent.picker.TimePickerFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class CreateEventActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    CreateEventView {

    lateinit var bdg: ActivityCreateEventBinding

    private val presenter by lazy { CreateEventPresenter(this) }

    private var imageUri: Uri? = null

    private var label: String = ""
    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        bdg.loading.root.visibility = View.VISIBLE

        findViewById<ImageView>(R.id.backButton).setOnClickListener { finish() }
        findViewById<TextView>(R.id.toolbarText).text = getString(R.string.createEventTitle)

        bdg.spinner.adapter = presenter.spinnerAdapter(this)
        bdg.spinner.onItemSelectedListener = this

        presenter.getCreatorOfEvent(Firebase.auth.currentUser!!.uid)

        bdg.selectDate.setOnClickListener {
            DatePickerFragment { day, month, year -> presenter.onDateSelected(bdg, day, month, year) }
                .show(supportFragmentManager, "datePicker")
        }

        bdg.selectTime.setOnClickListener {
            TimePickerFragment { hour, minutes -> presenter.onTimeSelected(bdg, hour, minutes) }
                .show(supportFragmentManager, "timePicker")
        }

        bdg.uploadImage.setOnClickListener {
            selectImageFromGallery(launcher)
        }

        bdg.createButton.setOnClickListener {
            closeKeyboard()
            bdg.errorMessage.visibility = View.GONE
            bdg.loadingCreating.visibility = View.VISIBLE
            bdg.createButton.isEnabled = false
            presenter.createEvent(
                this,
                bdg.title.text.toString(),
                bdg.description.text.toString(),
                bdg.otherInfo.text.toString(),
                bdg.address.text.toString(),
                label,
                bdg.dateText.text.toString(),
                bdg.timeText.text.toString(),
                userName,
                imageUri
            )
        }
    }

    override fun creatorOfEvent(name: String) {
        userName = name
        bdg.loading.root.visibility = View.GONE
    }

    override fun eventCreated() {
        setResult(CREATE_EVENT_ACTIVITY_REQUEST_CODE, Intent())
        finish()
    }

    override fun setErrorMessage(message: String) {
        bdg.loadingCreating.visibility = View.GONE
        bdg.errorMessage.visibility = View.VISIBLE
        bdg.errorMessage.text = message
        bdg.createButton.isEnabled = true
    }

    private fun closeKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        this.label = getLabelInEnglish(this, p0!!.getItemAtPosition(p2).toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    private val launcher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.data != null) {
            imageUri = result.data!!.data
            Glide.with(this).load(imageUri).into(bdg.image)
            bdg.image.visibility = View.VISIBLE
        }
    }

    override fun somethingWentWrong() {
        Toast.makeText(this, getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT).show()
        setErrorMessage(getString(R.string.somethingWentWrong))
    }
}
