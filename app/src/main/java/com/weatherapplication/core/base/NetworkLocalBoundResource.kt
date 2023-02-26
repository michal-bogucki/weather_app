package com.weatherapplication.core.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber

inline fun <DB, REMOTE, T> networkLocalBoundResource(
    crossinline fetchFromLocal: suspend () -> Flow<DB>,
    crossinline shouldFetchFromRemote: suspend (DB?) -> Boolean = { true },
    crossinline shouldFetchFromLocale: suspend (DB?) -> Boolean = { false },
    crossinline fetchFromRemote: suspend () -> Response<REMOTE>,
    crossinline saveFetchResult: suspend (REMOTE) -> Unit,
    crossinline changeToDomain: suspend (DB) -> (T),
) = flow<Resource<T>> {
    emit(Resource.loading())
    val data = fetchFromLocal().firstOrNull()
    val shouldFetchFromRemote1 = shouldFetchFromRemote(data)
    if (shouldFetchFromRemote1) {
        if (data != null && shouldFetchFromLocale(data)) {
            emit(Resource.success(changeToDomain(data)))
        }
        val fetchResult = fetchFromRemote()
        if (fetchResult.isSuccessful) {
            fetchResult.body()?.let {
                saveFetchResult(it)
            }
            emitAll(fetchFromLocal().map { dbData -> Resource.success(changeToDomain(dbData)) })
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
        emitAll(fetchFromLocal().map { dbData -> Resource.success(changeToDomain(dbData)) })
    }
}.catch {
    val v = it.message.toString()
    emit(Resource.error(v))
}

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun ResponseBody.stringSuspending() =
    withContext(Dispatchers.IO) { string() }
