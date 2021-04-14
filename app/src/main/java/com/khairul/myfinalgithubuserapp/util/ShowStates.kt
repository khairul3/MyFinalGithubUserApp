package com.khairul.myfinalgithubuserapp.util

import android.view.View
import com.khairul.myfinalgithubuserapp.databinding.FavoriteFragmentBinding
import com.khairul.myfinalgithubuserapp.databinding.FollowFragmentBinding
import com.khairul.myfinalgithubuserapp.databinding.HomeFragmentBinding

interface ShowStates {
    fun homeLoading(homeBinding: HomeFragmentBinding): Int? = null
    fun homeSuccess(homeBinding: HomeFragmentBinding): Int? = null
    fun homeError(homeBinding: HomeFragmentBinding, message: String?): Int? = null
    fun followLoading(followBinding: FollowFragmentBinding): Int? = null
    fun followSuccess(followBinding: FollowFragmentBinding): Int? = null
    fun followError(followBinding: FollowFragmentBinding, message: String?): Int? = null
    fun favoriteLoading(favoriteFragmentBinding: FavoriteFragmentBinding): Int? = null
    fun favoriteSuccess(favoriteFragmentBinding: FavoriteFragmentBinding): Int? = null
    fun favoriteError(favoriteFragmentBinding: FavoriteFragmentBinding, message: String?): Int? =
        null

    val gone: Int
        get() = View.GONE

    val visible: Int
        get() = View.VISIBLE
}