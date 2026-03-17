package com.example.truckercore.layers.data_2.repository.base

import android.util.Log
import com.example.truckercore.core.error.DataException
import com.example.truckercore.layers.data.base.dto.contracts.Dto
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSource
import com.example.truckercore.layers.domain.base.contracts.Entity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

abstract class DataRepositoryBase(protected open val remote: RemoteDataSource) {

    abstract val classTag: String

    //----------------------------------------------------------------------------------------------
    // Fetch single object
    //----------------------------------------------------------------------------------------------
    fun <D : Dto, E : Entity> fetch(
        data: D?,
        mapper: (dto: D) -> E
    ): DataOutcome<E> =
        try {
            data.mapToOutcome(mapper)
        } catch (exception: Exception) {
            exception.mapToOutcome(
                "Failed to fetch single object in repository [$classTag]. " +
                        "DTO type: ${data?.javaClass?.simpleName}, data: ${data?.toString() ?: "null"}"
            )
        }

    //----------------------------------------------------------------------------------------------
    // Fetch list of objects
    //----------------------------------------------------------------------------------------------
    fun <D : Dto, E : Entity> fetchAll(
        data: List<D>?,
        mapper: (dtos: List<D>) -> List<E>
    ): DataOutcome<List<E>> =
        try {
            data.mapToOutcome(mapper)
        } catch (exception: Exception) {
            exception.mapToOutcome(
                "Failed to fetch list of objects in repository [$classTag]. " +
                        "DTO type: ${data?.firstOrNull()?.javaClass?.simpleName ?: "unknown"}, " +
                        "items count: ${data?.size ?: 0}"
            )
        }

    //----------------------------------------------------------------------------------------------
    // Observe single object
    //----------------------------------------------------------------------------------------------
    fun <D : Dto, E : Entity> observe(
        dataFlow: Flow<D?>,
        mapper: (dto: D) -> E
    ): Flow<DataOutcome<E>> =
        dataFlow.map { dto ->
            dto.mapToOutcome(mapper)
        }.catch { throwable ->
            emit(
                throwable.mapToOutcome(
                    "Failed to observe single object in repository [$classTag]. " +
                            "Flow type: ${dataFlow.javaClass.simpleName}"
                )
            )
        }

    //----------------------------------------------------------------------------------------------
    // Observe list of objects
    //----------------------------------------------------------------------------------------------
    fun <D : Dto, E : Entity> observeAll(
        dataFlow: Flow<List<D>?>,
        mapper: (dtos: List<D>) -> List<E>
    ): Flow<DataOutcome<List<E>>> =
        dataFlow.map { dtos ->
            dtos.mapToOutcome(mapper)
        }.catch { throwable ->
            emit(
                throwable.mapToOutcome(
                    "Failed to observe list of objects in repository [$classTag]. " +
                            "Flow type: ${dataFlow.javaClass.simpleName}"
                )
            )
        }

    //----------------------------------------------------------------------------------------------
    // Internal mapping helpers
    //----------------------------------------------------------------------------------------------
    private fun Throwable.mapToOutcome(message: String): DataOutcome.Failure {
        Log.e(classTag, message, this) // full stack trace for debugging
        val appError = DataException.Repository(message, this)
        return DataOutcome.Failure(appError)
    }

    private fun <D : Dto, E : Entity> D?.mapToOutcome(mapper: (D) -> E): DataOutcome<E> =
        if (this == null) {
            DataOutcome.Empty
        } else {
            DataOutcome.Success(mapper(this))
        }

    private fun <D : Dto, E : Entity> List<D>?.mapToOutcome(mapper: (List<D>) -> List<E>): DataOutcome<List<E>> =
        if (isNullOrEmpty()) {
            DataOutcome.Empty
        } else {
            DataOutcome.Success(mapper(this))
        }

}