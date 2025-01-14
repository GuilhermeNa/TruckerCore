package com.example.truckercore.infrastructure.database.firebase.interfaces

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface FirebaseRepository<T : Dto> {

    /**
     * Creates a new document in the specified Firestore collection using the provided DTO.
     * The DTO is assigned a new ID, and the document is saved to Firestore.
     *
     * @param dto The DTO to be saved. The DTO will have its ID assigned during the creation.
     * @return A [Flow] of [Response], containing the document ID on success or an error on failure.
     */
    fun create(dto: T): Flow<Response<String>>

    /**
     * Updates an existing document in the specified Firestore collection with the provided DTO.
     *
     * @param dto The DTO containing the data to update the document.
     * @return A [Flow] of [Response], indicating the result of the update operation.
     */
    fun update(dto: T): Flow<Response<Unit>>

    /**
     * Deletes a document from the specified Firestore collection by ID.
     *
     * @param id The ID of the document to delete.
     * @return A [Flow] of [Response], indicating the result of the deletion operation.
     */
    fun delete(id: String): Flow<Response<Unit>>

    /**
     * Checks whether an entity exists in the Firestore collection based on its ID.
     *
     * @param id The ID of the document to check for existence.
     * @return A [Flow] of [Response], indicating whether the entity exists.
     */
    suspend fun entityExists(id: String): Flow<Response<Boolean>>

    /**
     * Fetches a single document from the Firestore collection by ID.
     *
     * @param id The ID of the document to fetch.
     * @return A [Flow] of [Response], containing the data.
     */
    suspend fun simpleDocumentFetch(id: String): Flow<Response<T>>

    /**
     * Fetches a list of documents from the Firestore collection based on a query using a specific field and value.
     * It performs a query to find documents where the specified field matches the given value.
     *
     * @param field The field to query in the Firestore collection.
     * @param value The value to search for in the specified field.
     * @return A [Flow] of [Response], containing the data.
     */
    suspend fun simpleQueryFetch(field: Field, value: String): Flow<Response<List<T>>>

}