package com.example.truckercore.model.infrastructure.integration.data.for_app.repository

import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSource
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore._utils.classes.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing a high-level data access layer.
 *
 * Serves as an abstraction over a concrete [DataSource], providing a unified contract for
 * fetching and observing DTOs in a standardized [AppResponse] wrapper. This enables consistent
 * error handling and response interpretation across different layers of the application.
 *
 * @see DataSource
 * @see Specification
 * @see AppResponse
 */
interface DataRepository {

    /**
     * Fetches a single DTO that matches the given [Specification].
     *
     * ### Example:
     * ```kotlin
     * val response = repository.findOneBy(spec)
     * when (response) {
     *     is AppResponse.Success -> {
     *         val user = response.data
     *         // Use the user DTO
     *     }
     *     is AppResponse.Empty -> {
     *         // No user found with the given ID
     *     }
     *     is AppResponse.Error -> {
     *         // Handle the error
     *         logError(response.throwable)
     *     }
     * }
     * ```
     *
     * @param T The type of DTO to retrieve.
     * @param spec The criteria used to find the DTO.
     * @return [AppResponse] containing the result, or error if applicable.
     */
    suspend fun <T : BaseDto> findOneBy(spec: Specification<T>): AppResponse<T>

    /**
     * Fetches all DTOs that match the given [Specification].
     *
     * ### Example:
     * ```kotlin
     * val response = repository.findAllBy(spec)
     * when (response) {
     *     is AppResponse.Success -> {
     *         val users = response.data
     *         // Use the list of active users
     *     }
     *     is AppResponse.Empty -> {
     *         // No active users found
     *     }
     *     is AppResponse.Error -> {
     *         // Handle the error
     *         logError(response.throwable)
     *     }
     * }
     * ```
     *
     * @param T The type of DTOs to retrieve.
     * @param spec The filtering criteria for the query.
     * @return [AppResponse] containing a list of DTOs or an appropriate state.
     */
    suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): AppResponse<List<T>>

    /**
     * Observes changes to a single DTO in real-time that matches the [Specification].
     *
     * ### Example:
     * ```kotlin
     * val flow = repository.flowOneBy(spec)
     * flow.collect { response ->
     *     when (response) {
     *         is AppResponse.Success -> {
     *             val user = response.data
     *             // React to updates to this user
     *         }
     *         is AppResponse.Empty -> {
     *             // The user was deleted or not found
     *         }
     *         is AppResponse.Error -> {
     *             // Handle real-time error
     *         }
     *     }
     * }
     * ```
     *
     * @param T The type of DTO to observe.
     * @param spec The criteria used to subscribe to changes.
     * @return [Flow] emitting [AppResponse] on each update.
     */
    fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<AppResponse<T>>

    /**
     * Observes changes to a list of DTOs in real-time that match the [Specification].
     *
     * ### Example:
     * ```kotlin
     * val flow = repository.flowAllBy(spec)
     * flow.collect { response ->
     *     when (response) {
     *         is AppResponse.Success -> {
     *             val users = response.data
     *             // Update UI with new user list
     *         }
     *         is AppResponse.Empty -> {
     *             // No matching users at this time
     *         }
     *         is AppResponse.Error -> {
     *             // Handle the error
     *         }
     *     }
     * }
     * ```
     *
     * @param T The type of DTOs to observe.
     * @param spec The criteria used to subscribe to list updates.
     * @return [Flow] emitting [AppResponse] containing updated lists.
     */
    fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<AppResponse<List<T>>>

}