package com.weatherapplication.core.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

inline fun <DB, REMOTE> networkLocalBoundResource(
    crossinline fetchFromLocal: suspend () -> Flow<DB>,
    crossinline shouldFetchFromRemote: suspend (DB?) -> Boolean = { true },
    crossinline fetchFromRemote: suspend () -> Response<REMOTE>,
    crossinline saveFetchResult: suspend (REMOTE) -> Unit,
) = flow<Resource<DB>> {
    emit(Resource.loading())
    val data = fetchFromLocal().firstOrNull()
    if (shouldFetchFromRemote(data)) {
        if (data != null)
            emit(Resource.success(data))
        val fetchResult = fetchFromRemote()
        if (fetchResult.isSuccessful) {
            fetchResult.body()?.let {
                saveFetchResult(it)
            }
            emitAll(fetchFromLocal().map { dbData -> Resource.success(dbData) })
        } else {

            val msg = fetchResult.errorBody()?.stringSuspending()
            val errorMsg = if (msg.isNullOrEmpty()) {
                fetchResult.message()
            } else {
                msg
            }
            emit(Resource.error(errorMsg))
        }
    } else {
        emitAll(fetchFromLocal().map { dbData -> Resource.success(dbData) })
    }
}

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun ResponseBody.stringSuspending() =
    withContext(Dispatchers.IO) { string() }
