package com.ftbw.app.bestworld.view.create.createpost

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.ActivityCreatePostBinding
import com.ftbw.app.bestworld.helper.ImagePickerHelper.selectImageFromGallery
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreatePostActivity : AppCompatActivity(), CreatePostView {

    lateinit var bdg: ActivityCreatePostBinding

    private val presenter by lazy { CreatePostPresenter(this) }

    private var imageUri: Uri? = null
    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(bdg.root)

        bdg.loading.root.visibility = View.VISIBLE
        presenter.getCreatorOfEvent(Firebase.auth.currentUser!!.uid)

        bdg.backButton.setOnClickListener { finish() }
        bdg.uploadImage.setOnClickListener { selectImageFromGallery(launcher) }

        bdg.publishButton.setOnClickListener {
            closeKeyboard()
            bdg.publishButton.isEnabled = false
            bdg.errorMessage.visibility = View.GONE
            bdg.publishLoading.visibility = View.VISIBLE
            presenter.publishPost(imageUri, bdg.postText.text.toString(), userName)
        }
    }

    override fun creatorOfEvent(name: String) {
        userName = name
        bdg.loading.root.visibility = View.GONE
        Toast.makeText(this, userName, Toast.LENGTH_SHORT).show()
    }

    override fun postCreated() {
        finish()
    }

    private fun closeKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private val launcher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.data != null) {
            imageUri = result.data!!.data
            Glide.with(this).load(imageUri).into(bdg.postImage)
            bdg.postImage.visibility = View.VISIBLE
        }
    }

    override fun setErrorMessage() {
        bdg.publishLoading.visibility = View.GONE
        bdg.errorMessage.visibility = View.VISIBLE
        bdg.publishButton.isEnabled = true
    }

    override fun somethingWentWrong() {
        Toast.makeText(this, getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT).show()
    }
}
