package com.khairul.myfinalgithubuserapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.khairul.myfinalgithubuserapp.R
import com.khairul.myfinalgithubuserapp.adapter.UserAdapter
import com.khairul.myfinalgithubuserapp.databinding.FollowFragmentBinding
import com.khairul.myfinalgithubuserapp.util.ShowStates
import com.khairul.myfinalgithubuserapp.util.State.*
import com.khairul.myfinalgithubuserapp.util.TypeView
import com.khairul.myfinalgithubuserapp.viewModel.FollowViewModel

class FollowFragment : Fragment(), ShowStates {
    private lateinit var binding: FollowFragmentBinding
    private lateinit var users: UserAdapter
    private lateinit var followViewModel: FollowViewModel
    private lateinit var name: String
    private var type: String? = null

    companion object {
        private const val TYPE = "type"
        private const val USERNAME = "username"
        fun newInstance(name: String, type: String) =
            FollowFragment().also {
                it.arguments = Bundle().apply {
                    putString(USERNAME, name)
                    putString(TYPE, type)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(USERNAME).toString()
            type = it.getString(TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FollowFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]

        users = UserAdapter(arrayListOf()) { user, _ -> }
        binding.recylerFollow.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = users
        }

        when {
            type == resources.getString(R.string.following) -> followViewModel.setFollow(
                name,
                TypeView.FOLLOWING
            )
            type == resources.getString(R.string.followers) -> followViewModel.setFollow(
                name,
                TypeView.FOLLOWER
            )
            else -> followError(binding, null)
        }
        follow()
    }

    override fun followLoading(followBinding: FollowFragmentBinding): Int? {
        followBinding.also {
            it.errLayout.mainNotFound.visibility = gone
            it.progress.start()
            it.recylerFollow.visibility = gone
        }
        return super.followLoading(followBinding)
    }

    private fun follow() {
        followViewModel.dataFollow.observe(viewLifecycleOwner, Observer {
            when (it.state) {
                SUCCESS -> when {
                    !it.data.isNullOrEmpty() -> {
                        followSuccess(binding)
                        users.setData(it.data)
                    }
                    else -> {
                        followError(binding, resources.getString(R.string.not_have, name, type))
                    }
                }
                LOADING -> followLoading(binding)
                ERROR -> followError(binding, it.message)
            }
        })
    }


    override fun followSuccess(followBinding: FollowFragmentBinding): Int? {
        followBinding.also {
            it.errLayout.mainNotFound.visibility = gone
            it.progress.stop()
            it.recylerFollow.visibility = visible
        }
        return super.followSuccess(followBinding)
    }

    override fun followError(followBinding: FollowFragmentBinding, message: String?): Int? {
        followBinding.also { followFragmentBinding ->
            followFragmentBinding.errLayout.also {
                it.mainNotFound.visibility = visible
                it.emptyText.text = message ?: resources.getString(R.string.not_found)
            }
            followFragmentBinding.progress.stop()
            followFragmentBinding.recylerFollow.visibility = gone
        }
        return super.followError(followBinding, message)
    }
}