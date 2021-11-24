package com.ftbw.app.bestworld.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.RowUsersRecyclerBinding
import com.ftbw.app.bestworld.model.user.UserRecyclerDTO
import com.ftbw.app.bestworld.viewmodel.UsersViewModel

class RViewUsersAdapter(
    var context: Context,
    var viewModel: UsersViewModel,
    private var usersList: List<UserRecyclerDTO>
) :
    RecyclerView.Adapter<RViewUsersAdapter.UserHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_users_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(context, viewModel, usersList[position])
    }

    override fun getItemCount() = usersList.size

    class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bdg = RowUsersRecyclerBinding.bind(view)

        fun bind(context: Context, viewModel: UsersViewModel, user: UserRecyclerDTO) {
            bdg.name.text = user.name
            bdg.email.text = user.email
            if (user.imageURL == "") {
                bdg.profilePicture.setBackgroundResource(R.drawable.ic_user_name)
            } else {
                Glide.with(context).load(user.imageURL).into(bdg.profilePicture)
            }

            bdg.row.setOnClickListener {
                viewModel.goToUserProfileByKey(user.key)
            }
        }
    }
}