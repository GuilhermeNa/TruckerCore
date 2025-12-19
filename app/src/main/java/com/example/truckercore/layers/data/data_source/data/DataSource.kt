package com.example.truckercore.layers.data.data_source.data

import com.example.truckercore.core.error.InfraException
import com.example.truckercore.core.error.core.ErrorMapper
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.specification._contracts.ApiSpecificationWrapper
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.data.base.specification._contracts.SpecificationInterpreter
import kotlinx.coroutines.flow.Flow

/**
 * Abstract data source that defines standard operations for querying data from a backend.
 *
 * This class uses the Specification pattern to build queries in a generic and reusable way,
 * delegating the specifics of the query construction to a [SpecificationInterpreter].
 *
 * Subclasses should implement the actual data retrieval logic (e.g., Firebase, REST API, database).
 *
 * @property interpreter Responsible for translating [Specification] objects into
 * API-specific query or document references.
 * @property errorMapper Maps low-level exceptions into domain/application-specific exceptions.
 */
abstract class DataSource(
    protected val interpreter: SpecificationInterpreter,
    protected val errorMapper: ErrorMapper
) {

    /**
     * Finds a single entity by its identifier or filter specified in [spec].
     *
     * @param spec The specification defining the query.
     * @return The entity as a [BaseDto], or `null` if not found.
     */
    abstract suspend fun <D : BaseDto> findById(spec: Specification<D>): D?

    /**
     * Finds multiple entities matching the criteria specified in [spec].
     *
     * @param spec The specification defining the query.
     * @return A list of entities as [BaseDto], or `null` if no results found.
     */
    abstract suspend fun <D : BaseDto> findAllBy(spec: Specification<D>): List<D>?

    /**
     * Returns a [Flow] that emits updates for a single entity matching the specification.
     *
     * @param spec The specification defining the query.
     * @return A [Flow] emitting the entity as [BaseDto], or `null` if it does not exist.
     *
     * The flow is **cold** and starts listening to updates only when collected.
     * Listeners are removed automatically when the flow collection is cancelled.
     */
    abstract fun <D : BaseDto> flowOneBy(spec: Specification<D>): Flow<D?>

    /**
     * Returns a [Flow] that emits updates for multiple entities matching the specification.
     *
     * @param spec The specification defining the query.
     * @return A [Flow] emitting a list of entities as [BaseDto], or `null` if no results exist.
     *
     * The flow is **cold** and starts listening to updates only when collected.
     * Listeners are removed automatically when the flow collection is cancelled.
     */
    abstract fun <D : BaseDto> flowAllBy(spec: Specification<D>): Flow<List<D>?>

    /**
     * Translates a generic [Specification] into an API-specific wrapper (document or query).
     *
     * This method uses the [SpecificationInterpreter] to obtain the corresponding
     * [ApiSpecificationWrapper] instance (e.g., [DocumentWrapper] or [QueryWrapper]).
     *
     * @throws InfraException.Specification if the resulting wrapper type does not match [D].
     */
    protected inline fun <reified D : ApiSpecificationWrapper> getApiSpecification(spec: Specification<*>): D {
        val result = spec.entityId?.let {
            interpreter.byId(it, spec.collection)
        } ?: interpreter.byFilter(spec.getFilter(), spec.collection)

        if (result !is D) throw InfraException.Specification(
            "Expected ${D::class.simpleName}, but got ${result::class.simpleName}"
        )

        return result
    }

}