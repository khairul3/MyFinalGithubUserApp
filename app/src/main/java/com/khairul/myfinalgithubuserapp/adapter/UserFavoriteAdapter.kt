package com.khairul.myfinalgithubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khairul.myfinalgithubuserapp.databinding.UserFavListBinding
import com.khairul.myfinalgithubuserapp.model.GithubUserModel

class UserFavoriteAdapter(
    private val Data: ArrayList<GithubUserModel>,
    private val clickListener: (String, View) -> Unit
) : RecyclerView.Adapter<UserFavoriteAdapter.UsersViewHolder>() {

    fun setData(items: List<GithubUserModel>) {
        Data.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            UserFavListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class UsersViewHolder(private val binding: UserFavListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUserModel, click: (String, View) -> Unit) {
            binding.data = user
            binding.root.transitionName = user.login
            binding.root.setOnClickListener { click(user.login, binding.root) }
        }
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) =
        holder.bind(Data[position], clickListener)

    override fun getItemCount(): Int = Data.size


}