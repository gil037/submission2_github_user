package com.example.github2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github2.R
import com.example.github2.databinding.ItemUserBinding
import com.example.github2.data.remote.response.User

class UserAdapter(
    private val listUser: List<User>,
    private val onItemClickCallback: OnItemClickCallback
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        fun bind(login: String, avatar: String) {
            binding.apply {
                nameTextview.text = login
                Glide.with(itemView.context)
                    .load(avatar)
                    .placeholder(R.drawable.placeholder_image)
                    .into(photoUserImageview)
            }
        }
    }

    interface OnItemClickCallback {
        fun onClick(login: String, id: Int, avatar: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val (login, avatar, id) = listUser[position]
        holder.bind(login, avatar)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onClick(login, id, avatar)
        }
    }

    override fun getItemCount() = listUser.size
}