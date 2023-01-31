package com.weatherapplication.core.base

sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T> : Resource<T>()
    class Error<T>(throwable: String) : Resource<T>(error = throwable)

    companion object {
        fun <T> success(data: T): Resource<T> = Success(data)
        fun <T> error(error: String): Resource<T> = Error(error)
        fun <T> loading(): Resource<T> = Loading()
    }
}
