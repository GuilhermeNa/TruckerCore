package com.example.truckercore.shared.interfaces

import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface Repository<T : Dto> {

    /**
     * Creates a new entity entity in the database.
     *
     * @param dto The data transfer object (DTO) containing the entity information to be created.
     * This method generates an ID for the entity and stores it in the database.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully created.
     * - [Response.Error] when the object creation fails.
     */
    suspend fun create(dto: T): Flow<Response<String>>

    /**
     * Updates an existing Entity in the database.
     *
     * @param dto The data transfer object (DTO) containing the updated information.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully updated.
     * - [Response.Error] when the object update fails.
     */
    suspend fun update(dto: T): Flow<Response<Unit>>

    /**
     * Deletes an Entity entity from the database by its ID.
     *
     * @param id The unique identifier (ID) of the entity to be deleted.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully deleted.
     * - [Response.Error] when the object delete fails.
     */
    suspend fun delete(id: String): Flow<Response<Unit>>

    /**
     * Checks if an Entity exists in the database by its ID.
     *
     * This method queries the database to check whether an entity with the given ID
     * exists or not. It returns a boolean response wrapped in a [Flow]. If the entity
     * exists, the response will emit `true`; otherwise, it will emit `false`.
     *
     * @param id The unique identifier (ID) of the entity to check for existence.
     * @return A [Flow] of:
     * - [Response.Success] when the object exists.
     * - [Response.Empty] when the object does not exist.
     * - [Response.Error] when any error occurs.
     */
    suspend fun entityExists(id: String): Flow<Response<Unit>>

    /**
     * Fetches an Entity by its ID.
     *
     * @param id The unique identifier (ID) of the entity to be fetched.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully found.
     * - [Response.Error] when any error occurs.
     * - [Response.Empty] when the data was not found.
     */
    suspend fun fetchById(id: String): Flow<Response<T>>

}