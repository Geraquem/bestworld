package com.ftbw.app.bestworld.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityLoginBinding
import com.ftbw.app.bestworld.helper.EventCommon.Companion.LOGIN_ACTIVITY_REQUEST_CODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var bdg: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    private val presenter by lazy { LoginPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        auth = Firebase.auth

        findViewById<ImageView>(R.id.backButton).setOnClickListener { finish() }
        findViewById<TextView>(R.id.toolbarText).text = getString(R.string.log_in_title)

        bdg.logInButton.setOnClickListener {
            closeKeyboard()
            presenter.checkCredentials(bdg.email.text.toString(), bdg.password.text.toString())
//            presenter.checkCredentials("a@gmail.com", "123456")
        }

        bdg.registerText.setOnClickListener {
            returnIntent(true)
        }
    }

    override fun doLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                returnIntent(false)
            } else {
                presenter.loginUnSuccessful()
            }
        }
    }

    override fun updateUI() {
        bdg.errorMessage.visibility = View.GONE
        bdg.loading.visibility = View.VISIBLE
        bdg.logInButton.isEnabled = false
    }

    override fun loginUnSuccessful() {
        bdg.errorMessage.visibility = View.VISIBLE
        bdg.loading.visibility = View.GONE
        bdg.logInButton.isEnabled = true
    }

    private fun returnIntent(isRegister: Boolean) {
        val returnIntent = Intent().apply {
            putExtra("register", isRegister)
        }
        setResult(LOGIN_ACTIVITY_REQUEST_CODE, returnIntent)
        finish()
    }

    private fun closeKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}