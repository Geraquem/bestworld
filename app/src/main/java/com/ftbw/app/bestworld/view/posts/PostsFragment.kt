package com.ftbw.app.bestworld.view.posts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.FragmentPostsBinding
import com.ftbw.app.bestworld.model.post.PostDTO
import com.ftbw.app.bestworld.view.posts.adapter.RViewPostsAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostsFragment : Fragment(), PostsView {

    private var _bdg: FragmentPostsBinding? = null
    private val bdg get() = _bdg!!

    private lateinit var mContext: Context

    private val presenter by lazy { PostsPresenter(this) }

    private lateinit var adapter: RViewPostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bdg = FragmentPostsBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bdg.loading.root.visibility = View.VISIBLE

        if (Firebase.auth.currentUser != null) {
            presenter.getPosts(Firebase.auth.currentUser!!.uid)
            bdg.firstLogIn.visibility = View.GONE
        } else {
            bdg.firstLogIn.visibility = View.VISIBLE
            bdg.loading.root.visibility = View.GONE
        }
    }

    override fun showPosts(posts: List<PostDTO>) {
        bdg.loading.root.visibility = View.GONE
        if (posts.isEmpty()) {
            bdg.suchEmpty.root.visibility = View.VISIBLE
        } else {
            bdg.suchEmpty.root.visibility = View.GONE
            bdg.recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = RViewPostsAdapter({}, {}, requireContext(), posts)
            bdg.recyclerView.adapter = adapter
        }
    }

    override fun somethingWentWrong() {
        Toast.makeText(mContext, R.string.somethingWentWrong, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}