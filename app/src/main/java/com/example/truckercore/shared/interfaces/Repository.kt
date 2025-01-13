package com.example.truckercore.shared.interfaces

import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal interface Repository<T: Dto> {

    /**
     * Creates a new entity entity in the database.
     *
     * @param dto The data transfer object (DTO) containing the entity information to be created.
     * This method generates an ID for the entity and stores it in the database.
     * @return The ID of the created object.
     */
    suspend fun create(dto: T): Flow<Response<String>>

    /**
     * Updates an existing Entity in the database.
     *
     * @param dto The data transfer object (DTO) containing the updated information.
     */
    fun update(dto: T): Flow<Response<Unit>>

    /**
     * Deletes an Entity entity from the database by its ID.
     *
     * @param id The unique identifier (ID) of the entity to be deleted.
     */
    fun delete(id: String): Flow<Response<Unit>>

    /**
     * Checks if an Entity exists in the database by its ID.
     *
     * This method queries the database to check whether an entity with the given ID
     * exists or not. It returns a boolean response wrapped in a [Flow]. If the entity
     * exists, the response will emit `true`; otherwise, it will emit `false`.
     *
     * @param id The unique identifier (ID) of the entity to check for existence.
     * @return A [Flow] that emits a [Response] containing a [Boolean].
     *         The [Boolean] will be `true` if the entity exists, `false` otherwise.
     */
    suspend fun entityExists(id: String): Flow<Response<Boolean>>

    /**
     * Fetches an Entity by its ID.
     *
     * @param id The unique identifier (ID) of the entity to be fetched.
     * @return A [Flow] that emits a [Response] containing the StorageFileDto entity.
     * If an error occurs, an [Error] response is emitted instead.
     */
    suspend fun fetchById(id: String): Flow<Response<T>>

}