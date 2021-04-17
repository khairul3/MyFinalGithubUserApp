package com.khairul.myfinalgithubuserapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.khairul.myfinalgithubuserapp.model.GithubUserModel
import com.khairul.myfinalgithubuserapp.repository.UserRepositories
import com.khairul.myfinalgithubuserapp.util.Result

class HomeViewModel : ViewModel() {
    private val name: MutableLiveData<String> = MutableLiveData()

    val searchResult: LiveData<Result<List<GithubUserModel>>> = Transformations
        .switchMap(name) {
            UserRepositories.searchUsers(it)
        }

    fun setSearch(query: String) {
        when (name.value) {
            query -> {
                return
            }
            else -> name.value = query
        }
    }
}