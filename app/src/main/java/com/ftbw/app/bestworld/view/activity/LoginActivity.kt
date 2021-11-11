package com.ftbw.app.bestworld.view.activity

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
import com.ftbw.app.bestworld.helper.BottomNavHelper.Companion.LOGIN_ACTIVITY_REQUEST_CODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var bdg: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        auth = Firebase.auth

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish();
        }
        findViewById<TextView>(R.id.toolbarText).text = getString(R.string.log_in_title)

        bdg.errorMessage.visibility = View.GONE
        bdg.loading.visibility = View.GONE
        bdg.logInButton.isEnabled = true

        bdg.logInButton.setOnClickListener {
            closeKeyboard()
            bdg.errorMessage.visibility = View.GONE

//            val email = bdg.email.text.toString()
//            val password = bdg.password.text.toString()

            val email = "a@gmail.com"
            val password = "123456"

            if (email.isNotEmpty() && password.isNotEmpty()) {
                bdg.loading.visibility = View.VISIBLE
                bdg.logInButton.isEnabled = false
                doLogin(email, password)
            }
        }

        bdg.registerText.setOnClickListener {
            bdg.errorMessage.visibility = View.GONE
            returnIntent(true)
        }
    }

    private fun doLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    returnIntent(false)

                } else {
                    bdg.errorMessage.visibility = View.VISIBLE
                    bdg.loading.visibility = View.GONE
                    bdg.logInButton.isEnabled = true
                }
            }
    }

    private fun returnIntent(register: Boolean) {
        val returnIntent = Intent().apply {
            putExtra("register", register)
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

    //HACER FIREBASE AUTH CON LINK EN EL CORREO
}