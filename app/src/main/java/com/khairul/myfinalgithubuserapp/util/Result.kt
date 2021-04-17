package com.khairul.myfinalgithubuserapp.util

import com.khairul.myfinalgithubuserapp.util.State.*
class Result<out E>(val state: State, val data: E?, val message: String?) {
    companion object {
        fun <E> success(data: E): Result<E> =
            Result(SUCCESS, data, null)

        fun <E> error(data: E?, message: String): Result<E> =
            Result(ERROR, data, message)

        fun <E> loading(data: E?): Result<E> =
            Result(LOADING, data, null)
    }

}