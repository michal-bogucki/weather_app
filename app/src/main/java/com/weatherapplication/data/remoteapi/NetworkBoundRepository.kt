package com.weatherapplication.data.remoteapi

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import retrofit2.Response

@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<RESULT, REQUEST> {

    fun asFlow() = flow<Resource<RESULT>> {


        val data = fetchFromLocal().firstOrNull()
        if (data != null)
            emit(Resource.Success(data))

        if (shouldFetch(data)) {

            val apiResponse = fetchFromRemote()
            val remotePosts = apiResponse.body()
            if (apiResponse.isSuccessful && remotePosts != null) {
                saveRemoteData(remotePosts)
            } else {
                emit(Resource.Failed(apiResponse.message()))
            }

            emitAll(
                    fetchFromLocal().map {
                        Resource.Success<RESULT>(it)
                    }
            )
        }

    }.catch { e ->
        e.printStackTrace()
        val message = e.message ?: ""
        emit(Resource.Failed(message))
    }

    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)

    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>

    @MainThread
    protected abstract fun shouldFetch(data: RESULT?): Boolean
}

