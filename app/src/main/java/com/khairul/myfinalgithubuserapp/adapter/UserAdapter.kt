package com.khairul.myfinalgithubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khairul.myfinalgithubuserapp.databinding.UserListBinding
import com.khairul.myfinalgithubuserapp.model.GithubUserModel


class UserAdapter(
    private val users: ArrayList<GithubUserModel>,
    private val clickListener: (String, View) -> Unit
) : RecyclerView.Adapter<UserAdapter.UsersViewHolder>() {
    fun setData(items: List<GithubUserModel>) {
        users.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) =
        holder.bind(users[position], clickListener)

    override fun getItemCount(): Int = users.size

    inner class UsersViewHolder(private val binding: UserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUserModel, click: (String, View) -> Unit) {
            binding.data = user
            binding.root.transitionName = user.login
            binding.root.setOnClickListener { click(user.login, binding.root) }
        }
    }


}