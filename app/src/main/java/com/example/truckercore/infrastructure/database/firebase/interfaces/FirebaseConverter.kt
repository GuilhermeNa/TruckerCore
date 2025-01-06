package com.example.truckercore.infrastructure.database.firebase.interfaces

import com.example.truckercore.shared.utils.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

/**
 * Interface for converting Firestore query results and document snapshots into model objects.
 *
 * This interface provides methods for processing Firestore query snapshots and document references,
 * converting them into appropriate model objects of type [T]. It handles both single document
 * retrieval and list retrieval scenarios, with the ability to return a [Response] containing the
 * results or an empty response if no data is found.
 *
 * @param T The type of model object to which Firestore data will be converted.
 */
internal interface FirebaseConverter<T> {

    /**
     * Processes a Firestore query snapshot that retrieves a list of documents.
     *
     * This method executes the query, waits for the result, and converts the query snapshot into a
     * list of model objects of type [T]. If the query snapshot is empty, it returns a [Response.Empty].
     *
     * @param nQuerySnapShot The Firestore query snapshot to be processed.
     * @return A [Response] containing a list of model objects if the query is successful, or
     *         [Response.Empty] if the snapshot contains no documents.
     * @throws NullQuerySnapShotException If the provided query snapshot is null.
     */
    fun processQuerySnapShot(nQuerySnapShot: QuerySnapshot?): Response<List<T>>

    /**
     * Processes a Firestore query snapshot that retrieves a single document.
     *
     * This method executes the query, waits for the result, and converts the first document
     * from the snapshot into a model object of type [T]. If the query snapshot is empty, it
     * returns [Response.Empty].
     *
     * @param nQuerySnapShot The Firestore query snapshot to be processed.
     * @return A [Response] containing the first model object if the query is successful, or
     *         [Response.Empty] if no document is found.
     * @throws NullQuerySnapShotException If the provided query snapshot is null.
     */
    fun processSingleQuerySnapShotItem(nQuerySnapShot: QuerySnapshot?): Response<T>

    /**
     * Processes a Firestore document snapshot and retrieves the document data.
     *
     * This method fetches the document snapshot from the provided reference, converts it into
     * a model object of type [T], and returns it wrapped in a [Response.Success]. If the document
     * is not found or an error occurs, it returns [Response.Empty].
     *
     * @param documentSnapShot The Firestore document snapshot to be processed.
     * @return A [Response] containing the model object if the document is found, or [Response.Empty]
     *         if the document is not found.
     * @throws NullDocumentSnapShotException If the provided document snapshot is null.
     */
    fun processDocumentSnapShot(documentSnapShot: DocumentSnapshot?): Response<T>

}