package com.khairul.consumergithubapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.khairul.consumergithubapp.data.UserDataSource
import com.khairul.consumergithubapp.model.GithubUserModel
import kotlinx.coroutines.Dispatchers

class UserRepository(private val source: UserDataSource) {
    fun getUserList():LiveData<List<GithubUserModel>> = liveData(Dispatchers.IO){
        emit(source.getUsers())
    }
}