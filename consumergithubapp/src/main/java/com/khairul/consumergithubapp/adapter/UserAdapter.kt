package com.khairul.consumergithubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khairul.consumergithubapp.databinding.UserListBinding
import com.khairul.consumergithubapp.model.GithubUserModel

class UserAdapter(
    private val githubUserModels: ArrayList<GithubUserModel>,
    private val clickListener: (GithubUserModel) -> Unit
) : RecyclerView.Adapter<UserAdapter.UsersViewHolder>() {

    inner class UsersViewHolder(private val binding: UserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(githubUserModel: GithubUserModel, click: (GithubUserModel) -> Unit) {
            binding.data = githubUserModel
            binding.root.transitionName = githubUserModel.login
            binding.root.setOnClickListener { click(githubUserModel) }
        }
    }

    fun setData(items: List<GithubUserModel>) {
        githubUserModels.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            UserListBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) =
        holder.bind(githubUserModels[position], clickListener)

    override fun getItemCount(): Int = githubUserModels.size
}