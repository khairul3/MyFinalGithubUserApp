package com.khairul.myfinalgithubuserapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khairul.myfinalgithubuserapp.model.GithubUserModel

@Database(entities = [GithubUserModel::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val mInstance = INSTANCE
            if (mInstance != null)
                return mInstance
            synchronized(UserDatabase::class) {
                val mbuilder = Room.databaseBuilder(
                    context.applicationContext, UserDatabase::class.java, "myGithub_db"
                ).build()
                INSTANCE = mbuilder
                return mbuilder
            }
        }
    }
    abstract fun userDao(): UserDao


}