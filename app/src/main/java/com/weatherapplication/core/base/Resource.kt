package com.weatherapplication.core.base

sealed interface Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>
    object Loading : Resource<Nothing>
    class Error(val throwable: String) : Resource<Nothing>

    companion object {
        fun <T> success(data: T): Resource<T> = Success(data)
        fun error(error: String): Resource<Nothing> = Error(error)
        fun loading(): Resource<Nothing> = Loading
    }
}
