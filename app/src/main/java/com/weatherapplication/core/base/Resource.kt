package com.weatherapplication.core.base

sealed interface Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>
    class Loading<T> : Resource<T>
    class Error<out T>(val throwable: String) : Resource<T>

    companion object {
        fun <T> success(data: T): Resource<T> = Success(data)
        fun <T> error(error: String): Resource<T> = Error(error)
        fun <T> loading(): Resource<T> = Loading()
    }
}
