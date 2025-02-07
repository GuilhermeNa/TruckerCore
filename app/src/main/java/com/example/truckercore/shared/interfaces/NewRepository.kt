package com.example.truckercore.shared.interfaces

import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface NewRepository {

    /**
     * Creates a new entity in the repository using the provided DTO.
     * The DTO will be saved, and a new ID will be generated for it.
     *
     * @param dto The DTO containing the data to create the new entity.
     * @return A [Flow] of:
     * - [Response.Success] containing the generated ID if the entity was successfully created.
     * - [Response.Error] if an error occurs during creation.
     */
    suspend fun <T : Dto> create(dto: T): Flow<Response<String>>

    /**
     * Updates an existing entity in the repository with the provided DTO.
     * The DTO contains the updated data for the entity.
     *
     * @param dto The DTO containing the updated data for the entity.
     * @return A [Flow] of:
     * - [Response.Success] when the entity was successfully updated.
     * - [Response.Error] if an error occurs during the update.
     */
    suspend fun <T : Dto> update(dto: T): Flow<Response<Unit>>

    /**
     * Deletes an entity from the repository by its ID.
     *
     * @param id The ID of the entity to delete.
     * @return A [Flow] of:
     * - [Response.Success] when the entity was successfully deleted.
     * - [Response.Error] if an error occurs during the deletion.
     */
    suspend fun delete(id: String): Flow<Response<Unit>>

    /**
     * Checks whether an entity exists in the repository by its ID.
     *
     * @param id The ID of the entity to check for existence.
     * @return A [Flow] of:
     * - [Response.Success] if the entity exists.
     * - [Response.Empty] if the entity does not exist.
     * - [Response.Error] if an error occurs during the existence check.
     */
    suspend fun entityExists(id: String): Flow<Response<Unit>>

    /**
     * Fetches an entity from the repository by its ID.
     * Returns the data as a generic object in the response.
     *
     * @param params The document parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [Response.Success] containing the entity data.
     * - [Response.Error] if an error occurs during the fetch.
     * - [Response.Empty] if no entity is found for the given ID.
     */
    suspend fun fetchByDocument(params: DocumentParameters): Flow<Response<*>>

    /**
     * Fetches a list of entities from the repository based on a query.
     * The query is constructed using the provided query settings.
     *
     * @param params The query parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [Response.Success] containing a list of entities that match the query.
     * - [Response.Error] if an error occurs during the query.
     * - [Response.Empty] if no entities match the query.
     */
    suspend fun fetchByQuery(params: QueryParameters): Flow<Response<List<*>>>

}