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

    private fun getFailureOutcome(e: Throwable): DataOutcome.Failure {
        val error = if (e is AppException) {
            e
        } else {
            DataException.Unknown(message = UNKNOWN, cause = e)
        }
        return DataOutcome.Failure(error)
    }

    //----------------------------------------------------------------------------------------------
    // FETCH
    //----------------------------------------------------------------------------------------------
    fun <D : Dto, E : Entity> fetch(
        data: D?,
        toEntity: (dto: D) -> E
    ): DataOutcome<E> =
        try {
            if (data == null) DataOutcome.Empty
            else DataOutcome.Success(toEntity(data))

        } catch (e: Exception) {
            getFailureOutcome(e)
        }

    fun <D : Dto, E : Entity> fetchAll(
        data: List<D>?,
        toEntities: (dtos: List<D>) -> List<E>,
    ): DataOutcome<List<E>> = try {
        if (data.isNullOrEmpty()) DataOutcome.Empty
        else DataOutcome.Success(toEntities(data))

    } catch (e: Exception) {
        getFailureOutcome(e)
    }

    //----------------------------------------------------------------------------------------------
    // OBSERVE
    //----------------------------------------------------------------------------------------------
    fun <D : Dto, E : Entity> observe(
        dataFlow: Flow<D?>,
        toEntity: (dto: D) -> E
    ): Flow<DataOutcome<E>> {
        return dataFlow.map { dto ->
            if (dto == null) DataOutcome.Empty
            else DataOutcome.Success(toEntity(dto))

        }.catch { e ->
            emit(getFailureOutcome(e))
        }
    }

    fun <D : Dto, E : Entity> observeAll(
        dataFlow: Flow<List<D>?>,
        toEntities: (dtos: List<D>) -> List<E>
    ): Flow<DataOutcome<List<E>>> {
        return dataFlow.map { dtos ->
            if (dtos.isNullOrEmpty()) DataOutcome.Empty
            else DataOutcome.Success(toEntities(dtos))

        }.catch { e ->
            emit(getFailureOutcome(e))
        }
    }

}