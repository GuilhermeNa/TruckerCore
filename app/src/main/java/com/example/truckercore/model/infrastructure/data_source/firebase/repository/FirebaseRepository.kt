package com.example.truckercore.model.infrastructure.data_source.firebase.repository

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.FirebaseConversionException
import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.FirebaseRequestException
import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseRequest
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines various operations to interact with Firestore documents and collections.
 * The methods in this interface cover CRUD operations (Create, Read, Update, Delete) and
 * querying capabilities, including streaming, for Firestore collections and documents.
 */
internal interface FirebaseRepository {

    /**
     * Runs a Firebase transaction operation.
     *
     * This method allows you to perform a series of operations in a transaction on Firebase Firestore.
     * A transaction ensures that a series of reads and writes to Firestore are atomic; if one operation
     * fails, the entire transaction will be rolled back.
     *
     * @param transactionOperation The operation that will be executed within the transaction.
     * @return A [Flow] emitting a [AppResponse] containing the result of the operation (successful or failed).
     */
    fun runTransaction(transactionOperation: (transaction: Transaction) -> Unit): Flow<AppResponse<Unit>>

    /**
     * Creates a new document in the specified Firestore collection.
     *
     * This method creates a document in a Firestore collection and returns a [DocumentReference] pointing to
     * the newly created document. If no [documentPath] is provided, a new document with an automatically
     * generated ID will be created. If a [documentPath] is provided, the document will be created with that
     * specific ID.
     *
     * @param collection The [Collection] enum value representing the Firestore collection in which
     *                   the document should be created.
     * @param documentPath An optional document ID. If provided, the document will be created with
     *                     this specific ID. If not provided, a new document with an automatically
     *                     generated ID will be created.
     * @return A [DocumentReference] representing the newly created document in the specified collection.
     */
    fun createBlankDocument(collection: Collection, documentPath: String? = null): DocumentReference

    /**
     * Creates a new document in the specified Firestore collection using the provided DTO.
     * The DTO is assigned a new ID, and the document is saved to Firestore.
     *
     * @param collection The Firestore collection where the document will be created.
     * @param dto The DTO to be saved. The DTO will have its ID assigned during the creation.
     * @return A [Flow] that emits a [AppResponse.Success] when the document is successfully created,
     *         containing the ID of the created document.
     * @throws IncompleteTaskException If the task of creating the document is not successful.
     */
    fun create(collection: Collection, dto: Dto): Flow<AppResponse<String>>

    /**
     * Updates an existing document in the specified Firestore collection with the provided DTO.
     *
     * @param collection The Firestore collection where the document will be updated.
     * @param dto The DTO containing the data to update the document.
     * @return A [Flow] that emits a [AppResponse.Success] when the document is successfully updated.
     * @throws IncompleteTaskException If the task of updating the document is not successful.
     */
    fun update(collection: Collection, dto: Dto): Flow<AppResponse<Unit>>

    /**
     * Deletes a document from the specified Firestore collection by ID.
     *
     * @param collection The Firestore collection from which the document will be deleted.
     * @param id The ID of the document to delete.
     * @return A [Flow] that emits a [AppResponse.Success] when the document is successfully deleted.
     * @throws IncompleteTaskException If the task of deleting the document is not successful.
     */
    fun delete(collection: Collection, id: String): Flow<AppResponse<Unit>>

    /**
     * Checks whether an entity exists in the specified Firestore collection based on its ID.
     *
     * @param collection The Firestore collection to check for existence.
     * @param id The ID of the document to check for existence.
     * @return A [Flow] that emits:
     * - [AppResponse.Success] if the document exists.
     * - [AppResponse.Empty] if the document does not exist.
     */
    fun entityExists(collection: Collection, id: String): Flow<AppResponse<Unit>>

    /**
     * Fetches the logged user document, with an option to stream the data continuously.
     *
     * @param userId The ID of the user whose document is being fetched.
     * @param shouldStream Boolean value indicating whether the user data should be streamed.
     * @return A [Flow] of:
     *  - [AppResponse.Success] representing the fetched user data.
     *  - [AppResponse.Empty] if the document does not exist.
     */
    fun fetchLoggedUser(userId: String, shouldStream: Boolean): Flow<AppResponse<UserDto>>

    /**
     * Fetches a single document from Firestore based on the provided [FirebaseRequest].
     *
     * @param firebaseRequest The request object containing collection, parameters, and other details for the document retrieval.
     * @return A [Flow] of:
     *  - [AppResponse.Success] that represents the fetched document.
     *  - [AppResponse.Empty] if the document does not exist.
     * @throws FirebaseRequestException If the Firebase request is invalid or malformed.
     * @throws FirebaseConversionException If the document snapshot cannot be converted into the specified DTO class.
     */
    fun <T : Dto> documentFetch(firebaseRequest: FirebaseRequest<T>): Flow<AppResponse<T>>

    /**
     * Fetches a list of documents from Firestore based on the provided [FirebaseRequest].
     * This method also allows querying multiple documents with specific parameters.
     *
     * @param firebaseRequest The request object containing collection, parameters, and other details for the query.
     * @return A [Flow] of:
     *  - [AppResponse.Success] representing the list of fetched documents.
     * - [AppResponse.Empty] if the document does not exist.
     * @throws FirebaseRequestException If the Firebase request is invalid or malformed.
     * @throws FirebaseConversionException If the document snapshot cannot be converted into the specified DTO class.
     */
    fun <T : Dto> queryFetch(firebaseRequest: FirebaseRequest<T>): Flow<AppResponse<List<T>>>

}