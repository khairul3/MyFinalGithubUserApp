package com.khairul.myfinalgithubuserapp.model

import android.os.Parcelable
import com.khairul.myfinalgithubuserapp.model.GithubUserModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchingModel(
    val total_count: String,
    val incomplete_results: Boolean? = null,
    val items: List<GithubUserModel>
) : Parcelable