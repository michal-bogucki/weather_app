package com.weatherapplication.core.base

import kotlinx.coroutines.flow.flow
import retrofit2.Response

inline fun <REMOTE> networkResource(
    crossinline fetchFromRemote: suspend () -> Response<REMOTE>,
) = flow<Resource<REMOTE>> {
    emit(Resource.loading())
    val fetchResult = fetchFromRemote()
    if (fetchResult.isSuccessful) {
        fetchResult.body()?.let {
            emit(Resource.success(it))
        }
    } else {
        val msg = fetchResult.errorBody()?.string()
        val errorMsg = if (msg.isNullOrEmpty()) {
            fetchResult.message()
        } else {
            msg
        }
        emit(Resource.error(errorMsg))
    }
}
