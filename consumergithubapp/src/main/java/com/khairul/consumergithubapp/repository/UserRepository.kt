package com.khairul.consumergithubapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.khairul.consumergithubapp.datasource.UserDataSource
import com.khairul.consumergithubapp.model.GithubUser
import kotlinx.coroutines.Dispatchers

class UserRepository(private val source: UserDataSource) {
    fun getUserList():LiveData<List<GithubUser>> = liveData(Dispatchers.IO){
        emit(source.getUsers())
    }
}