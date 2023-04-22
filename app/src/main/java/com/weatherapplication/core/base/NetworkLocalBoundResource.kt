package com.weatherapplication.core.base

import com.weatherapplication.core.network.ConnectionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

inline fun <DB, REMOTE, T> networkLocalBoundResource(
    crossinline fetchFromLocal: suspend () -> Flow<DB>,
    crossinline shouldFetchFromRemote: suspend (DB?) -> Boolean = { true },
    crossinline shouldFetchFromLocale: suspend (DB?) -> Boolean = { false },
    crossinline fetchFromRemote: suspend () -> Response<REMOTE>,
    crossinline saveFetchResult: suspend (REMOTE) -> Unit,
    crossinline changeToDomain: suspend (DB) -> (T),
    crossinline isNetwork: suspend () -> Flow<ConnectionState>
) = flow<Resource<T>> {
    isNetwork().collect {
        when (it) {
            ConnectionState.Available -> {
                val data = fetchFromLocal().firstOrNull()
                if (shouldFetchFromRemote(data)) {
                    emit(Resource.loading())
                    if (data != null && shouldFetchFromLocale(data)) {
                        emit(Resource.success(changeToDomain(data)))
                    }
                    val fetchResult = fetchFromRemote()
                    if (fetchResult.isSuccessful) {
                        fetchResult.body()?.let {
                            saveFetchResult(it)
                        }
                        emitAll(
                            fetchFromLocal().map { dbData -> Resource.success(
                                changeToDomain(dbData)
                            ) }
                        )
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
                    emitAll(
                        fetchFromLocal().map { dbData -> Resource.success(changeToDomain(dbData)) }
                    )
                }
            }

            ConnectionState.Unavailable -> {
                emit(Resource.error("network error"))
            }
        }
    }
}.catch {
    val v = it.message.toString()
    emit(Resource.error(v))
}

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun ResponseBody.stringSuspending() =
    withContext(Dispatchers.IO) { string() }
