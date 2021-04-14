package com.khairul.myfinalgithubuserapp.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.khairul.myfinalgithubuserapp.model.GithubUserModel

@Dao
interface UserDao {
    @Query("SELECT * from user_table ORDER BY login ASC")
    fun getUserList(): LiveData<List<GithubUserModel>>

    @Query("SELECT * from user_table WHERE login = :username")
    fun getUserDetail(username: String): GithubUserModel?

    @Query("SELECT * from user_table ORDER BY login ASC")
    fun getWidgetList(): List<GithubUserModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: GithubUserModel)

    @Delete
    suspend fun deleteUser(model: GithubUserModel): Int

    @Query("SELECT * from user_table ORDER BY login ASC")
    fun getUserListProvider(): Cursor


}