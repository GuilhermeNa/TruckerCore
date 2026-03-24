package com.example.truckercore.layers.data_2.repository.base

import android.util.Log
import com.example.truckercore.core.error.DataException
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSource
import com.example.truckercore.layers.domain.base.contracts.BaseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

abstract class DataRepositoryBase(protected open val remote: RemoteDataSource) {

    private val classTag = this::class.simpleName

    //----------------------------------------------------------------------------------------------
    // Fetch single object
    //----------------------------------------------------------------------------------------------
    fun <D : BaseDto, E : BaseEntity> fetch(
        data: D?,
        mapper: (dto: D) -> E
    ): DataOutcome<E> =
        try {
            data.mapToOutcome(mapper)
        } catch (exception: Exception) {
            exception.logAndMapToOutcome(
                "Failed to fetch single object in repository [$classTag]. " +
                        "DTO type: ${data?.javaClass?.simpleName}, data: ${data?.toString() ?: "null"}"
            )
        }

    //----------------------------------------------------------------------------------------------
    // Fetch list of objects
    //----------------------------------------------------------------------------------------------
    fun <D : BaseDto, E : BaseEntity> fetchAll(
        data: List<D>?,
        mapper: (dtos: List<D>) -> List<E>
    ): DataOutcome<List<E>> =
        try {
            data.mapToOutcome(mapper)
        } catch (exception: Exception) {
            exception.logAndMapToOutcome(
                "Failed to fetch list of objects in repository [$classTag]. " +
                        "DTO type: ${data?.firstOrNull()?.javaClass?.simpleName ?: "unknown"}, " +
                        "items count: ${data?.size ?: 0}"
            )
        }

    //----------------------------------------------------------------------------------------------
    // Observe single object
    //----------------------------------------------------------------------------------------------
    fun <D : BaseDto, E : BaseEntity> observe(
        dataFlow: Flow<D?>,
        mapper: (dto: D) -> E
    ): Flow<DataOutcome<E>> =
        dataFlow.map { dto ->
            dto.mapToOutcome(mapper)
        }.catch { throwable ->
            emit(
                throwable.logAndMapToOutcome(
                    "Failed to observe single object in repository [$classTag]. " +
                            "Flow type: ${dataFlow.javaClass.simpleName}"
                )
            )
        }

    //----------------------------------------------------------------------------------------------
    // Observe list of objects
    //----------------------------------------------------------------------------------------------
    fun <D : BaseDto, E : BaseEntity> observeAll(
        dataFlow: Flow<List<D>?>,
        mapper: (dtos: List<D>) -> List<E>
    ): Flow<DataOutcome<List<E>>> =
        dataFlow.map { dtos ->
            dtos.mapToOutcome(mapper)
        }.catch { throwable ->
            emit(
                throwable.logAndMapToOutcome(
                    "Failed to observe list of objects in repository [$classTag]. " +
                            "Flow type: ${dataFlow.javaClass.simpleName}"
                )
            )
        }

    //----------------------------------------------------------------------------------------------
    // Internal mapping helpers
    //----------------------------------------------------------------------------------------------
    private fun Throwable.logAndMapToOutcome(message: String): DataOutcome.Failure {
        Log.e(classTag, message, this) // full stack trace for debugging
        val appError = DataException.Repository(message, this)
        return DataOutcome.Failure(appError)
    }

    private fun <D : BaseDto, E : BaseEntity> D?.mapToOutcome(mapper: (D) -> E): DataOutcome<E> =
        if (this == null) {
            DataOutcome.Empty
        } else {
            DataOutcome.Success(mapper(this))
        }

    private fun <D : BaseDto, E : BaseEntity> List<D>?.mapToOutcome(mapper: (List<D>) -> List<E>): DataOutcome<List<E>> =
        if (isNullOrEmpty()) {
            DataOutcome.Empty
        } else {
            DataOutcome.Success(mapper(this))
        }

}