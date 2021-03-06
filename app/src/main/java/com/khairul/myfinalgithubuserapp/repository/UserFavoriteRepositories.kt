package com.khairul.myfinalgithubuserapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.khairul.myfinalgithubuserapp.database.UserDao
import com.khairul.myfinalgithubuserapp.model.GithubUserModel
import com.khairul.myfinalgithubuserapp.network.Configuration
import com.khairul.myfinalgithubuserapp.util.Result
import kotlinx.coroutines.Dispatchers

class UserFavoriteRepositories(private val data: UserDao) {
    private val fave: MutableLiveData<Boolean> = MutableLiveData()

    fun getDetailUser(username: String) = liveData(Dispatchers.IO) {
        emit(Result.loading(null))
        val user = data.getUserDetail(username)
        if (user != null) {
            fave.postValue(true)
            emit(Result.success(user))
        } else {
            fave.postValue(false)
            try {
                emit(Result.success(Configuration.api.userDetail(username)))
            } catch (e: Exception) {
                emit(Result.error(null, e.message ?: "Error"))
            }
        }
    }

    suspend fun insert(user: GithubUserModel) {
        data.insertUser(user)
        fave.value = true
    }

    suspend fun delete(user: GithubUserModel) {
        data.deleteUser(user)
        fave.value = false
    }

    val isFavorite: LiveData<Boolean> = fave
}