package com.example.truckercore.model.infrastructure.integration.data.for_api

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
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
 * @see DataSourceSpecificationInterpreter
 * @see DataSourceErrorMapper
 */
abstract class DataSource(
    protected val interpreter: DataSourceSpecificationInterpreter,
    protected val errorMapper: DataSourceErrorMapper
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
    abstract suspend fun <T : BaseDto> findById(spec: Specification<T>): T?

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
    abstract suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): List<T>?

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
    abstract fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<T?>

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
    abstract fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<List<T>?>

}