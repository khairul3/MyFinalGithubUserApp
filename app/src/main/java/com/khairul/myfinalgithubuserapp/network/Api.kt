package com.khairul.myfinalgithubuserapp.network

import com.khairul.myfinalgithubuserapp.model.GithubUserModel
import com.khairul.myfinalgithubuserapp.model.SearchingModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") q: String?
    ): SearchingModel

    @GET("users/{username}")
    suspend fun userDetail(
        @Path("username") username: String?
    ): GithubUserModel

    @GET("users/{username}/followers",)
    suspend fun userFollower(
        @Path("username") username: String?
    ): List<GithubUserModel>

    @GET("users/{username}/following")
    suspend fun userFollowing(
        @Path("username") username: String?
    ): List<GithubUserModel>
}