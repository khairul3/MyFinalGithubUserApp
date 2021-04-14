package com.khairul.consumergithubapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.khairul.consumergithubapp.datasource.UserDataSource
import com.khairul.consumergithubapp.repository.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    init {
        val source = UserDataSource(application.contentResolver)
        repository = UserRepository(source)
    }

    var userLists = repository.getUserList()
}