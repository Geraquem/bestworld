package com.ftbw.app.bestworld.view.register

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityRegisterBinding
import com.ftbw.app.bestworld.model.user.UserDTO
import com.ftbw.app.bestworld.helper.Common.Companion.REGISTER_ACTIVITY_REQUEST_CODE
import com.ftbw.app.bestworld.helper.ImagePickerHelper.selectImageFromGallery
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity(), RegisterView {

    lateinit var bdg: ActivityRegisterBinding

    private var imageUri: Uri? = null

    private val presenter by lazy { RegisterPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        findViewById<ImageView>(R.id.backButton).setOnClickListener { finish() }
        findViewById<TextView>(R.id.toolbarText).text = getString(R.string.register_title)

        bdg.particular.isChecked = true

        bdg.profilePictureButton.setOnClickListener {
            selectImageFromGallery(launcher)
        }

        bdg.registerButton.setOnClickListener {
            closeKeyboard()
            presenter.checkCredentials(
                bdg.name.text.toString(),
                bdg.email.text.toString(),
                bdg.password.text.toString(),
                bdg.repeatedPassword.text.toString()
            )
        }
    }

    override fun doRegister(name: String, email: String, password: String) {
        bdg.loading.visibility = View.VISIBLE
        bdg.registerButton.isEnabled = false
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    bdg.errorMessage.visibility = View.GONE
                    if (Firebase.auth.currentUser != null) {
                        val user = UserDTO(
                            name,
                            email,
                            "",
                            Firebase.auth.currentUser!!.uid,
                            "",
                            presenter.getRadioButton(bdg.company.isChecked),
                            0
                        )
                        presenter.saveUser(user, imageUri)
                    } else {
                        setErrorMessage(R.string.somethingWentWrong)
                    }
                } else {
                    setErrorMessage(R.string.errorMessageEmailInUse)
                }
            }
    }

    override fun userSavedOk() {
        setResult(REGISTER_ACTIVITY_REQUEST_CODE, Intent())
        finish()
    }

    override fun setErrorMessage(message: Int) {
        bdg.loading.visibility = View.GONE
        bdg.errorMessage.visibility = View.VISIBLE
        bdg.errorMessage.text = getString(message)
        bdg.registerButton.isEnabled = true
    }


    private fun closeKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.data != null) {
                imageUri = result.data!!.data
                Glide.with(this).load(imageUri).into(bdg.profilePicture)
                bdg.profilePicture.visibility = View.VISIBLE
            }
        }
}