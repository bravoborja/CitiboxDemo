package com.bravoborja.citiboxdemo.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.bravoborja.citiboxdemo.common.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import retrofit2.Response

@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<LocalData, RemoteData> {

    fun asFlow() = flow<State<LocalData>> {
        emit(State.loading())
        try {
            emit(State.success(fetchFromLocal().first()))
            val apiResponse = fetchFromRemote()
            val bodyResponse = apiResponse.body()
            if (apiResponse.isSuccessful && bodyResponse != null) {
                saveRemoteData(bodyResponse)
            } else {
                emit(State.error(apiResponse.message()))
            }
        } catch (e: Exception) {
            emit(State.error("Network error"))
        }
        emitAll(fetchFromLocal().map { State.success(it) })
    }

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<RemoteData>

    @WorkerThread
    protected abstract suspend fun saveRemoteData(data: RemoteData)

    @MainThread
    protected abstract fun fetchFromLocal(): Flow<LocalData>

}