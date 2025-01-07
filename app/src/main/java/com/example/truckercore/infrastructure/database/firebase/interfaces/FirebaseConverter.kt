package com.example.truckercore.infrastructure.database.firebase.interfaces

import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

internal interface FirebaseConverter<T> {

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