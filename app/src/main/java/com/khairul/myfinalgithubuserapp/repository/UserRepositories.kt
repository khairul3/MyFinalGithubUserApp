package com.khairul.myfinalgithubuserapp.repository

import androidx.lifecycle.liveData
import com.khairul.myfinalgithubuserapp.database.UserDao
import com.khairul.myfinalgithubuserapp.network.Configuration
import com.khairul.myfinalgithubuserapp.util.Result
import kotlinx.coroutines.Dispatchers

object UserRepositories {

    fun searchUsers(query: String) = liveData(Dispatchers.IO) {
        emit(Result.loading(null))
        try {
            val search = Configuration.api.searchUsers(query)
            emit(Result.success(search.items))
        } catch (e: Exception) {
            emit(Result.error(null, e.message ?: "Error Detected: ${e.localizedMessage}"))
        }
    }

    fun getFollowersList(username: String) = liveData(Dispatchers.IO) {
        emit(Result.loading(null))
        try {
            emit(Result.success(Configuration.api.userFollower(username)))
        } catch (e: Exception) {
            emit(Result.error(null, e.message ?: "Error Detected: ${e.localizedMessage}"))
        }
    }

    fun getFollowingList(username: String) = liveData(Dispatchers.IO) {
        emit(Result.loading(null))
        try {
            emit(Result.success(Configuration.api.userFollowing(username)))
        } catch (e: Exception) {
            emit(Result.error(null, e.message ?: "Error Detected: ${e.localizedMessage}"))
        }
    }

    fun getFavoriteList(userDao: UserDao) = userDao.getUserList()
}