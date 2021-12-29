package com.ftbw.app.bestworld.neworden.view.register

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
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityRegisterBinding
import com.ftbw.app.bestworld.helper.BottomNavHelper.Companion.REGISTER_ACTIVITY_REQUEST_CODE
import com.ftbw.app.bestworld.model.user.UserDTO
import com.ftbw.app.bestworld.neworden.repository.ImageController
import com.ftbw.app.bestworld.viewmodel.UsersViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    lateinit var bdg: ActivityRegisterBinding

    private var imageUri: Uri? = null

    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bdg.root)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish()
        }
        findViewById<TextView>(R.id.toolbarText).text = getString(R.string.register_title)

        bdg.particular.isChecked = true

        bdg.errorMessage.visibility = View.GONE
        bdg.loading.visibility = View.GONE

        bdg.profilePictureButton.setOnClickListener {
            ImageController.selectImageFromGallery(launcher)
        }

        bdg.registerButton.setOnClickListener {
            doRegister()
        }

        viewModel.isUserSaved.observe(this, {
            if (it) {
                setResult(REGISTER_ACTIVITY_REQUEST_CODE, Intent())
                finish()
            } else {
                setErrorMessage(R.string.somethingWentWrong)
            }
        })
    }

    private fun setErrorMessage(message: Int) {
        bdg.loading.visibility = View.GONE
        bdg.errorMessage.visibility = View.VISIBLE
        bdg.errorMessage.text = getString(message)
        bdg.registerButton.isEnabled = true
    }

    private fun doRegister() {
        val name = bdg.name.text.toString()
        val email = bdg.email.text.toString()
        val password = bdg.password.text.toString()
        val repeatedPassword = bdg.repeatedPassword.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatedPassword.isNotEmpty()) {
            closeKeyboard()
            bdg.registerButton.isEnabled = false
            bdg.loading.visibility = View.VISIBLE

            if (!isThereFailures(email, password, repeatedPassword)) {
                doRegisterAuth(name, email, password)
            }
        }
    }

    private fun isThereFailures(
        email: String,
        password: String,
        repeatedPassword: String
    ): Boolean {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setErrorMessage(R.string.errorMessageEmailNotValid)
            return true
        }
        if (password.length < 6) {
            setErrorMessage(R.string.errorMessagePasswords6chars)
            return true
        }
        if (password != repeatedPassword) {
            setErrorMessage(R.string.errorMessageNotMatchingPasswords)
            return true
        }
        return false
    }

    private fun doRegisterAuth(name: String, email: String, password: String) {
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
                            getRadioButton(),
                            0
                        )
                        viewModel.saveUser(user, imageUri)
                    } else {
                        setErrorMessage(R.string.somethingWentWrong)
                    }
                } else {
                    setErrorMessage(R.string.errorMessageEmailInUse)
                }
            }
    }

    private fun getRadioButton(): String {
        if (bdg.company.isChecked) {
            return "company"
        } else {
            return "particular"
        }
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