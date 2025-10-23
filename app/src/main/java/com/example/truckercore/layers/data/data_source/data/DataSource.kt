package com.example.truckercore.layers.data.data_source.data

import com.example.truckercore.core.error.InfraException
import com.example.truckercore.core.error.core.ErrorMapper
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.specification._contracts.ApiSpecificationWrapper
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.data.base.specification._contracts.SpecificationInterpreter
import kotlinx.coroutines.flow.Flow

/**
 * Abstract class representing a generic data source.
 *
 * This class must be implemented by any backend-specific data layer responsible for providing
 * data to the application. All data operations are driven by [Specification]s, which define the
 * structure and criteria for data queries.
 *
 * These specifications are interpreted into concrete backend query types, represented by [R1]
 * for single-entity lookups and [R2] for filtered multi-entity queries.
 *
 * @param R1 The backend query type used for unique lookups (e.g., `DocumentReference` in Firestore).
 * @param R2 The backend query type used for filtered queries (e.g., `Query` in Firestore).
 * @property interpreter Converts [Specification]s into backend-understandable queries ([R1], [R2]).
 * @property errorMapper Maps backend-specific exceptions into standardized [DataSourceException]s.
 *
 * @see Specification
 * @see SpecificationInterpreter
 * @see DataSourceErrorMapper
 */
abstract class DataSource(
    protected val interpreter: SpecificationInterpreter,
    protected val errorMapper: ErrorMapper
) {

    /**
     * Retrieves a single DTO based on the provided [Specification].
     *
     * ### Example:
     * ```kotlin
     * val response = dataSource.findById(spec)
     * if(response == null) {
     *      // data is not found in the specified criteria
     * } else {
     *      // the data is found and returned as DTO
     *      response = MyDto()...
     * }
     *  ```
     *
     * @param T The type of the DTO to retrieve.
     * @param spec The specification defining the lookup parameters.
     * @return The matching DTO, or `null` if not found.
     * @throws DataSourceException If any backend or mapping error occurs during retrieval.
     */
    abstract suspend fun <D : BaseDto> findById(spec: Specification<D>): D?

    /**
     * Retrieves all DTOs that match the given [Specification].
     *
     * ### Example:
     * ```kotlin
     * val response = dataSource.findAllBy(spec)
     * if(response == null) {
     *      // data is not found in the specified criteria
     * } else {
     *      // the data is found and returned as DTO List
     *      response = List(MyDto())...
     * }
     *  ```
     *
     * @param T The type of the DTOs to retrieve.
     * @param spec The specification defining the filtering criteria.
     * @return A list of matching DTOs, or `null` if none are found.
     * @throws DataSourceException If any backend or mapping error occurs during retrieval.
     */
    abstract suspend fun <D : BaseDto> findAllBy(spec: Specification<D>): List<D>?

    /**
     * Observes changes to a single DTO in real-time, based on the provided [Specification].
     *
     * ### Example:
     * ```kotlin
     * val response = dataSource.flowOneBy(spec)
     * response.collect { data ->
     *      if(data == null) {
     *          // data is not found in specified criteria
     *      } else {
     *          // data is found and returned a DTO
     *          response = MyDto()...
     *      }
     * }
     *  ```
     *
     * @param T The type of the DTO to observe.
     * @param spec The specification defining the lookup parameters.
     * @return A cold [Flow] emitting updates to the DTO, or `null` if it doesn't exist.
     * @throws DataSourceException If any backend or mapping error occurs during observation.
     */
    abstract fun <D : BaseDto> flowOneBy(spec: Specification<D>): Flow<D?>

    /**
     * Observes changes to a list of DTOs in real-time, based on the provided [Specification].
     *
     * ### Example:
     * ```kotlin
     * val response = dataSource.flowOneBy(spec)
     * response.collect { data ->
     *      if(data == null) {
     *          // data is not found in specified criteria
     *      } else {
     *          // data is found and returned a DTO List
     *          response = List(MyDto())...
     *      }
     * }
     *  ```
     *
     * @param T The type of the DTOs to observe.
     * @param spec The specification defining the filtering criteria.
     * @return A cold [Flow] emitting updated lists of matching DTOs, or `null` if none are found.
     * @throws DataSourceException If any backend or mapping error occurs during observation.
     */
    abstract fun <D : BaseDto> flowAllBy(spec: Specification<D>): Flow<List<D>?>

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