package com.ftbw.app.bestworld.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.databinding.RowUsersRecyclerBinding
import com.ftbw.app.bestworld.model.user.UserRecyclerDTO

class RViewUsersAdapter(var context: Context, private var usersList: List<UserRecyclerDTO>) :
    RecyclerView.Adapter<RViewUsersAdapter.UserHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_users_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(context, usersList[position])
    }

    override fun getItemCount() = usersList.size

    class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bdg = RowUsersRecyclerBinding.bind(view)

        fun bind(context: Context, user: UserRecyclerDTO) {
            //set imageURL
            bdg.name.text = user.name
            bdg.email.text = user.email

            bdg.row.setOnClickListener {
               /// IR AL PERFIL
            }
        }
    }
}