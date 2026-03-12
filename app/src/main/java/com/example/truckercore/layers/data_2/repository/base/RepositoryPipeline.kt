package com.example.truckercore.layers.data_2.repository.base

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.layers.data.base.dto.contracts.Dto
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.domain.base.contracts.Entity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class RepositoryPipeline {

    private companion object {
        private const val UNKNOWN = "An unknown error occurred in Data Pipeline."
    }

    //----------------------------------------------------------------------------------------------
    // FETCH
    //----------------------------------------------------------------------------------------------
    suspend fun <D : Dto, E : Entity> fetch(
        search: suspend () -> D?,
        toEntity: (dto: D) -> E,
    ): DataOutcome<E> = try {
        val dto = search()
        if (dto == null) DataOutcome.Empty
        else DataOutcome.Success(toEntity(dto))

    } catch (e: Exception) {
        val error = if (e is AppException) e
        else DataException.Unknown(message = UNKNOWN, cause = e)
        DataOutcome.Failure(error)
    }

    suspend fun <D : Dto, E : Entity> fetchAll(
        searchAll: suspend () -> List<D>?,
        toEntities: (dtos: List<D>) -> List<E>,
    ): DataOutcome<List<E>> = try {
        val dtos = searchAll()
        if (dtos.isNullOrEmpty()) DataOutcome.Empty
        else DataOutcome.Success(toEntities(dtos))

    } catch (e: Exception) {
        val error = if (e is AppException) e
        else DataException.Unknown(message = UNKNOWN, cause = e)
        DataOutcome.Failure(error)
    }

    //----------------------------------------------------------------------------------------------
    // OBSERVE
    //----------------------------------------------------------------------------------------------
    fun <D : Dto, E : Entity> observe(
        observe: () -> Flow<D?>,
        toEntity: (dto: D) -> E
    ): Flow<DataOutcome<E>> {
        return observe().map { dto ->
            if (dto == null) {
                DataOutcome.Empty
            } else {
                DataOutcome.Success(toEntity(dto))
            }

        }.catch { e ->
            val error = if (e is AppException) e
            else DataException.Unknown(message = UNKNOWN, cause = e)
            emit(DataOutcome.Failure(error))
        }
    }

    fun <D : Dto, E : Entity> observeAll(
        observe: () -> Flow<List<D>?>,
        toEntities: (dtos: List<D>) -> List<E>
    ): Flow<DataOutcome<List<E>>> = observe().map { dtos ->
        if (dtos.isNullOrEmpty()) DataOutcome.Empty
        else DataOutcome.Success(toEntities(dtos))

    }.catch { e ->
        val error = if (e is AppException) e
        else DataException.Unknown(message = UNKNOWN, cause = e)
        emit(DataOutcome.Failure(error))
    }

}