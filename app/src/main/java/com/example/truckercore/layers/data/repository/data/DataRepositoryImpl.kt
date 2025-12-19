package com.example.truckercore.layers.data.repository.data

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.mapper.base.MapperResolver
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.data.data_source.data.DataSource
import com.example.truckercore.layers.domain.base.contracts.entity.BaseEntity
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Implementation of [DataRepository] that interacts with a [DataSource] and
 * maps DTOs into domain entities using [MapperResolver].
 *
 * This class handles both synchronous retrieval (suspend functions) and reactive
 * streams (Flows), ensuring that exceptions are wrapped into domain-level [DataOutcome.Failure].
 */
class DataRepositoryImpl(private val dataSource: DataSource) : DataRepository {

    //----------------------------------------------------------------------------------------------
    // Find
    //----------------------------------------------------------------------------------------------
    override suspend fun <D : BaseDto, E : BaseEntity> findOneBy(spec: Specification<D>): DataOutcome<E> =
        try {
            // Retrieves data from the data source
            val dto = dataSource.findById(spec)
            // Converts it into the expected domain outcome
            dto.toOutcome(spec.dtoClass)

        } catch (e: AppException) {
            // Wraps an application exception into a domain outcome
            DataOutcome.Failure(e)

        } catch (e: Exception) {
            // Wraps any unexpected exception into an unknown error outcome
            handleUnknownError(e)
        }

    override suspend fun <D : BaseDto, E : BaseEntity> findAllBy(spec: Specification<D>): DataOutcome<List<E>> =
        try {
            // Retrieves data from the data source
            val dtos = dataSource.findAllBy(spec)
            // Converts it into the expected domain outcome
            dtos.toOutcome(spec.dtoClass)

        } catch (e: AppException) {
            // Wraps an application exception into a domain outcome
            DataOutcome.Failure(e)

        } catch (e: Exception) {
            // Wraps any unexpected exception into an unknown error outcome
            handleUnknownError(e)
        }

    //----------------------------------------------------------------------------------------------
    // Flow
    //----------------------------------------------------------------------------------------------
    override fun <D : BaseDto, E : BaseEntity> flowOneBy(spec: Specification<D>) =
        dataSource.flowOneBy(spec)
            .map { dto ->
                // Transforms each emitted DTO into a domain-level DataOutcome
                dto.toOutcome<D, E>(spec.dtoClass)
            }
            .catch { e ->
                // Converts any exception into a failure outcome
                handleFlowError(e)
            }

    override fun <D : BaseDto, E : BaseEntity> flowAllBy(
        spec: Specification<D>
    ) = dataSource.flowAllBy(spec)
        .map<List<D>?, DataOutcome<List<E>>> { dtos ->
            // Transforms the emitted list of DTOs into a domain-level DataOutcome
            dtos.toOutcome(spec.dtoClass)
        }
        .catch { t ->
            // Converts any exception into a failure outcome
            handleFlowError(t)
        }

    //----------------------------------------------------------------------------------------------
    // Helpers
    //----------------------------------------------------------------------------------------------
    private fun <D : BaseDto, E : BaseEntity> D?.toOutcome(dtoClazz: Class<D>): DataOutcome<E> {
        return if (this == null) {
            DataOutcome.Empty
        } else {
            val mapper = MapperResolver<D, E>(dtoClazz)
            val entity = mapper.toEntity(this)
            DataOutcome.Success(entity)
        }
    }

    private fun <D : BaseDto, E : BaseEntity> List<D>?.toOutcome(dtoClazz: Class<D>): DataOutcome<List<E>> {
        return if (this == null) {
            DataOutcome.Empty
        } else {
            val mapper = MapperResolver<D, E>(dtoClazz)
            val entity = mapper.toEntities(this)
            DataOutcome.Success(entity)
        }
    }

    private fun handleUnknownError(e: Throwable): DataOutcome.Failure {
        val appException = DataException.Unknown(message = UNKNOWN_ERROR_MSG, cause = e)
        return DataOutcome.Failure(appException)
    }

    private fun handleFlowError(e: Throwable) {
        if (e is AppException) DataOutcome.Failure(e)
        else handleUnknownError(e)
    }

    private companion object {
        private const val UNKNOWN_ERROR_MSG = "An unknown error occurred in Data Repository."
    }

}