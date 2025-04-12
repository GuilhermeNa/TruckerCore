package com.example.truckercore.model.shared.interfaces

import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

internal interface Repository {

    /**
     * Creates a new entity in the repository using the provided DTO.
     * The DTO will be saved, and a new ID will be generated for it.
     *
     * @param dto The DTO containing the data to create the new entity.
     * @return A [Flow] of:
     * - [AppResponse.Success] containing the generated ID if the entity was successfully created.
     */
    fun <T : Dto> create(dto: T): Flow<AppResponse<String>>

    /**
     * Updates an existing entity in the repository with the provided DTO.
     * The DTO contains the updated data for the entity.
     *
     * @param dto The DTO containing the updated data for the entity.
     * @return A [Flow] of:
     * - [AppResponse.Success] when the entity was successfully updated.
     */
    fun <T : Dto> update(dto: T): Flow<AppResponse<Unit>>

    /**
     * Deletes an entity from the repository by its ID.
     *
     * @param id The ID of the entity to delete.
     * @return A [Flow] of:
     * - [AppResponse.Success] when the entity was successfully deleted.
     * - [AppResponse.Error] if an error occurs during the deletion.
     */
    fun delete(id: String): Flow<AppResponse<Unit>>

    /**
     * Checks whether an entity exists in the repository by its ID.
     *
     * @param id The ID of the entity to check for existence.
     * @return A [Flow] of:
     * - [AppResponse.Success] if the entity exists.
     * - [AppResponse.Empty] if the entity does not exist.
     */
    fun entityExists(id: String): Flow<AppResponse<Unit>>

    /**
     * Fetches an entity from the repository by its ID.
     * Returns the data as a generic object in the response.
     *
     * @param documentParams The document parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [AppResponse.Success] containing the entity data.
     * - [AppResponse.Empty] if no entity is found for the given ID.
     */
    fun fetchByDocument(documentParams: DocumentParameters): Flow<AppResponse<*>>

    /**
     * Fetches a list of entities from the repository based on a query.
     * The query is constructed using the provided query settings.
     *
     * @param queryParams The query parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [AppResponse.Success] containing a list of entities that match the query.
     * - [AppResponse.Error] if an error occurs during the query.
     * - [AppResponse.Empty] if no entities match the query.
     */
    fun fetchByQuery(queryParams: QueryParameters): Flow<AppResponse<List<*>>>

}