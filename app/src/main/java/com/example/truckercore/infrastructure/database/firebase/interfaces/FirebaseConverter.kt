package com.example.truckercore.infrastructure.database.firebase.interfaces

import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.sealeds.Response
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

internal interface FirebaseConverter<T> {

    /**
     * Processes a Firestore task (e.g., a write or update task) and handles the result.
     *
     * This method processes the task result and, based on whether it was successful or not,
     * returns an appropriate [Response] that can either contain the document ID or indicate
     * a failure. If the task was unsuccessful, it will return an error.
     *
     * @param task The Firestore task that represents the operation result (e.g., write or update).
     * @param document An optional [DocumentReference] associated with the task, used to retrieve
     *                 the document ID if the operation was successful.
     * @return A [Response] that can be either a [Response.Success] with a document ID (for successful operations),
     *         or a [Response.Error] (for failed operations), or [Response.Empty] if the task did not result in
     *         any meaningful data.
     */
    fun processTask(task: Task<Void>, document: DocumentReference? = null): Response<*>

    /**
     * Processes a Firestore query snapshot that retrieves a list of documents.
     *
     * @param querySnapShot The Firestore query snapshot to be processed.
     * @return A [Response] containing a list of [Dto] objects if the query is successful, or [Response.Empty] if the snapshot contains no documents.
     * @throws NullQuerySnapShotException If the provided query snapshot is null.
     */
    fun processQuerySnapShot(querySnapShot: QuerySnapshot): Response<List<T>>

    /**
     * Processes a Firestore document snapshot and retrieves the document data.
     *
     * @param documentSnapShot The Firestore document snapshot to be processed.
     * @return A [Response] containing the [Dto] object if the document is found, or [Response.Empty] if the document is not found.
     * @throws NullDocumentSnapShotException If the provided document snapshot is null.
     */
    fun processDocumentSnapShot(documentSnapShot: DocumentSnapshot): Response<T>

    /**
     * Processes a Firestore document snapshot to check the existence of the document.
     *
     * @param documentSnapShot The Firestore document snapshot to be processed.
     * @return A [Response] containing true if the document exists, or [Response.Empty] if not.
     * @throws NullDocumentSnapShotException If the provided document snapshot is null.
     */
    fun processEntityExistence(documentSnapShot: DocumentSnapshot): Response<Boolean>

}