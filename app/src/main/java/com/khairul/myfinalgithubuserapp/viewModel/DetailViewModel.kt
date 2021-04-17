package com.khairul.myfinalgithubuserapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.khairul.myfinalgithubuserapp.database.UserDao
import com.khairul.myfinalgithubuserapp.database.UserDatabase
import com.khairul.myfinalgithubuserapp.model.GithubUserModel
import com.khairul.myfinalgithubuserapp.repository.UserFavoriteRepositories
import com.khairul.myfinalgithubuserapp.util.Result
import kotlinx.coroutines.launch

class DetailViewModel(app: Application) : AndroidViewModel(app) {

    private var userDao: UserDao = UserDatabase.getDatabase(app).userDao()
    private var userFavoriteRepos: UserFavoriteRepositories = UserFavoriteRepositories(userDao)
    fun data(username: String): LiveData<Result<GithubUserModel>> =
        userFavoriteRepos.getDetailUser(username)

    fun addFavorite(model: GithubUserModel) = viewModelScope.launch {
        userFavoriteRepos.insert(model)
    }

    fun removeFavorite(model: GithubUserModel) = viewModelScope.launch {
        userFavoriteRepos.delete(model)
    }

    val isFavorite: LiveData<Boolean> = userFavoriteRepos.isFavorite
}