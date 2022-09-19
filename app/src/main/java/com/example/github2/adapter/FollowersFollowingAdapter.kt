package com.example.github2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github2.R
import com.example.github2.data.remote.response.User
import com.example.github2.databinding.ItemUserBinding

class FollowersFollowingAdapter(
    private val listUser: List<User>
) : RecyclerView.Adapter<FollowersFollowingAdapter.FollowersFollowingViewHolder>() {

    class FollowersFollowingViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(user: User) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .placeholder(R.drawable.placeholder_image)
                    .into(photoUserImageview)
                nameTextview.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowersFollowingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FollowersFollowingViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowersFollowingViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount() = listUser.size
}