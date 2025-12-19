package com.example.truckercore.layers.data.repository.data

import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.domain.base.contracts.entity.BaseEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing and manipulating data in the domain layer.
 *
 * Provides both suspend-based and Flow-based methods to retrieve single or multiple
 * entities from a data source, applying a given [Specification] for querying.
 *
 * @see DataOutcome
 */
interface DataRepository {

    /**
     * Retrieves a single entity that matches the given [specification].
     *
     * @param D The type of the DTO used by the data source.
     * @param E The type of the domain entity returned.
     * @param spec The specification defining the query criteria.
     *
     * @return [DataOutcome.Success] containing the mapped entity if found,
     *         [DataOutcome.Empty] if no data is found, or
     *         [DataOutcome.Failure] if an exception occurs.
     */
    suspend fun <D : BaseDto, E : BaseEntity> findOneBy(spec: Specification<D>): DataOutcome<E>

    /**
     * Retrieves all entities that match the given [specification].
     *
     * @param D The type of the DTO used by the data source.
     * @param E The type of the domain entity returned.
     * @param spec The specification defining the query criteria.
     *
     * @return [DataOutcome.Success] containing the mapped list of entities,
     *         [DataOutcome.Empty] if no data is found, or
     *         [DataOutcome.Failure] if an exception occurs.
     */
    suspend fun <D : BaseDto, E : BaseEntity> findAllBy(spec: Specification<D>): DataOutcome<List<E>>

    /**
     * Returns a [Flow] emitting a single entity that matches the given [specification].
     *
     * The emitted data is mapped into a domain-level [DataOutcome].
     *
     * @param D The type of the DTO used by the data source.
     * @param E The type of the domain entity returned.
     * @param spec The specification defining the query criteria.
     *
     * @return A [Flow] emitting [DataOutcome.Success], [DataOutcome.Empty], or
     *         [DataOutcome.Failure] in case of an exception.
     */
    fun <D : BaseDto, E : BaseEntity> flowOneBy(spec: Specification<D>): Flow<DataOutcome<E>>

    /**
     * Returns a [Flow] emitting a list of entities that match the given [specification].
     *
     * The emitted data is mapped into a domain-level [DataOutcome].
     *
     * @param D The type of the DTO used by the data source.
     * @param E The type of the domain entity returned.
     * @param spec The specification defining the query criteria.
     *
     * @return A [Flow] emitting [DataOutcome.Success] with a list of entities,
     *         [DataOutcome.Empty] if no data is found, or
     *         [DataOutcome.Failure] in case of an exception.
     */
    fun <D : BaseDto, E : BaseEntity> flowAllBy(spec: Specification<D>): Flow<DataOutcome<List<E>>>

}

