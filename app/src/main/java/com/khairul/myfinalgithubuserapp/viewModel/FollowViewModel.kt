package com.khairul.myfinalgithubuserapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.khairul.myfinalgithubuserapp.model.GithubUserModel
import com.khairul.myfinalgithubuserapp.repository.UserRepositories
import com.khairul.myfinalgithubuserapp.util.Result
import com.khairul.myfinalgithubuserapp.util.TypeView
import com.khairul.myfinalgithubuserapp.util.TypeView.FOLLOWER
import com.khairul.myfinalgithubuserapp.util.TypeView.FOLLOWING

class FollowViewModel : ViewModel() {
    private val name: MutableLiveData<String> = MutableLiveData()
    private lateinit var type: TypeView
    val dataFollow: LiveData<Result<List<GithubUserModel>>> = Transformations
        .switchMap(name) {
            when (type) {
                FOLLOWER -> {
                    UserRepositories.getFollowersList(it)
                }
                FOLLOWING -> {
                    UserRepositories.getFollowingList(it)
                }
            }
        }

    fun setFollow(user: String, typeView: TypeView) {
        when (name.value) {
            user -> {
                return
            }
            else -> {
                name.value = user
                type = typeView
            }
        }
    }
}