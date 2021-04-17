package com.khairul.myfinalgithubuserapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.khairul.myfinalgithubuserapp.database.UserDatabase
import com.khairul.myfinalgithubuserapp.model.GithubUserModel
import com.khairul.myfinalgithubuserapp.repository.UserRepositories

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    val dataFavorite: LiveData<List<GithubUserModel>>

    init {
        val userDao = UserDatabase.getDatabase(app).userDao()
        dataFavorite = UserRepositories.getFavoriteList(userDao)
    }
}