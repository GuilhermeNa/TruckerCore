package com.example.truckercore.infrastructure.database.firebase.interfaces

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

interface NewFireBaseRepository {

    /**
     * Creates a new document in the specified Firestore collection using the provided DTO.
     * The DTO is assigned a new ID, and the document is saved to Firestore.
     *
     * @param collection The Firestore collection where the document will be created.
     * @param dto The DTO to be saved. The DTO will have its ID assigned during the creation.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully created.
     * - [Response.Error] when the object creation fails.
     */
    suspend fun create(collection: Collection, dto: Dto): Flow<Response<String>>

    /**
     * Updates an existing document in the specified Firestore collection with the provided DTO.
     *
     * @param collection The Firestore collection where the document will be updated.
     * @param dto The DTO containing the data to update the document.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully updated.
     * - [Response.Error] when the object update fails.
     */
    suspend fun update(collection: Collection, dto: Dto): Flow<Response<Unit>>

    /**
     * Deletes a document from the specified Firestore collection by ID.
     *
     * @param collection The Firestore collection from which the document will be deleted.
     * @param id The ID of the document to delete.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully deleted.
     * - [Response.Error] when the object delete fails.
     */
    suspend fun delete(collection: Collection, id: String): Flow<Response<Unit>>

    /**
     * Checks whether an entity exists in the specified Firestore collection based on its ID.
     *
     * @param collection The Firestore collection to check for existence.
     * @param id The ID of the document to check for existence.
     * @return A [Flow] of:
     * - [Response.Success] when the object exists.
     * - [Response.Empty] when the object does not exist.
     * - [Response.Error] when any error occurs.
     */
    suspend fun entityExists(collection: Collection, id: String): Flow<Response<Unit>>

    /**
     * Fetches a single document from the specified Firestore collection by ID.
     *
     * @param collection The Firestore collection to fetch the document from.
     * @param id The ID of the document to fetch.
     * @param clazz The class type of the DTO to map the document data into.
     * @return A [Flow] of:
     * - [Response.Success] when the object is successfully found.
     * - [Response.Error] when any error occurs.
     * - [Response.Empty] when the data was not found.
     */
    suspend fun <T : Dto> documentFetch(
        collection: Collection,
        id: String,
        clazz: Class<T>
    ): Flow<Response<T>>

    /**
     * Fetches a list of documents from the specified Firestore collection based on a query using the provided query settings.
     *
     * @param collection The Firestore collection to query.
     * @param settings The list of query settings to filter the documents.
     * @param clazz The class type of the DTO to map the documents data into.
     * @return A [Flow] of:
     * - [Response.Success] containing a list of matching documents.
     * - [Response.Error] when any error occurs.
     * - [Response.Empty] when no matching documents are found.
     */
    suspend fun <T : Dto> queryFetch(
        collection: Collection,
        settings: List<QuerySettings>,
        clazz: Class<T>
    ): Flow<Response<List<T>>>

}