package com.khairul.myfinalgithubuserapp.util

import com.khairul.myfinalgithubuserapp.util.State.*

class Resource<out E>(val state: State, val data: E?, val message: String?) {
    companion object {
        fun <E> success(data: E): Resource<E> = Resource(SUCCESS, data, null)
        fun <E> error(data: E?, message: String): Resource<E> = Resource(ERROR, data, message)
        fun <E> loading(data: E?): Resource<E> = Resource(LOADING, data, null)
    }

}