package com.ceylonsolutions.postmate.data.model

import com.ceylonsolutions.postmate.data.type.Status

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
                Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> = Resource(status = Status.LOADING, data = data, message = null)

        fun <T> noInternet(data: T?, message: String): Resource<T> =
                Resource(status = Status.NO_INTERNET, data = null, message = message)
    }
}