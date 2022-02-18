package com.ftbw.app.bestworld.view.userprofile.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.RowPostRecyclerBinding
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.ftbw.app.bestworld.model.post.PostDTO

class RViewPostsAdapter(
    val onLikeClick: (event: EventRecyclerDTO) -> Unit,
    val onCommentClick: (event: EventRecyclerDTO) -> Unit,
    var context: Context,
    private var postsList: List<PostDTO>
) :
    RecyclerView.Adapter<RViewPostsAdapter.PostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        return PostHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_post_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.bind(context, postsList[position])
        holder.bdg.row.setOnClickListener {
            //onClick(postsList[position])
        }
    }

    override fun getItemCount() = postsList.size

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bdg = RowPostRecyclerBinding.bind(view)

        fun bind(context: Context, post: PostDTO) {
            if (post.imageURL == "") {
                bdg.postImage.visibility = View.GONE
            } else {
                Glide.with(context).load(post.imageURL).into(bdg.postImage)
                bdg.postImage.visibility = View.VISIBLE
            }
            bdg.userName.text = post.creatorName
            bdg.postText.text = post.text
            bdg.likeCount.text = post.likesCount.toString()
        }
    }
}