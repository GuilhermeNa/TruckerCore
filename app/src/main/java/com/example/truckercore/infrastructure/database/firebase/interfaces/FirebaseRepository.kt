package com.example.truckercore.infrastructure.database.firebase.interfaces

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal interface FirebaseRepository<T : Dto> {

    /**
     * Creates a new document in the specified Firestore collection using the provided DTO.
     * The DTO is assigned a new ID, and the document is saved to Firestore.
     *
     * @param dto The DTO to be saved.
     * @return The ID of the newly created document.
     */
    fun create(dto: T): String

    /**
     * Updates an existing document in the specified Firestore collection with the provided DTO.
     *
     * @param dto The DTO to update the document with.
     */
    fun update(dto: T)

    /**
     * Deletes a document from the specified Firestore collection by ID.
     *
     * @param id The ID of the document to delete.
     */
    fun delete(id: String)

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