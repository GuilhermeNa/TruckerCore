package com.example.truckercore.infrastructure.database.firebase.util

import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseConversionException
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

/**
 * A utility class for converting Firestore document and query snapshots into DTO (Data Transfer Object) objects.
 * This class provides methods to process query results (QuerySnapshot) and individual document snapshots (DocumentSnapshot),
 * converting them into either a list or a single DTO object based on the specified type.
 */
internal class FirebaseConverter {

    /**
     * Processes a Firestore query snapshot that retrieves a list of documents.
     * It checks if the snapshot contains documents and converts them into a list of DTO objects.
     *
     * @param querySnapShot The Firestore query snapshot to be processed.
     * @param clazz The class type of the DTO to map the document data into.
     * @return A [Response] containing:
     * - [Response.Success] with a list of [T] if the query contains documents.
     * - [Response.Empty] if the snapshot contains no documents.
     * @throws FirebaseConversionException If the document snapshot cannot be converted into the specified DTO class.
     */
    fun <T> processQuerySnapShot(
        querySnapShot: QuerySnapshot,
        clazz: Class<T>
    ): Response<List<T>> =
        if (!querySnapShot.isEmpty) {
            val data = convertToList(querySnapShot, clazz)
            Response.Success(data)
        } else {
            Response.Empty
        }

    /**
     * Processes a Firestore document snapshot and retrieves the document data.
     * It checks if the document exists and converts it into a DTO object.
     *
     * @param documentSnapShot The Firestore document snapshot to be processed.
     * @param clazz The class type of the DTO to map the document data into.
     * @return A [Response] containing:
     * - [Response.Success] with the [T] object if the document exists.
     * - [Response.Empty] if the document does not exist.
     * @throws FirebaseConversionException If the document snapshot cannot be converted into the specified DTO class.
     */
    fun <T> processDocumentSnapShot(
        documentSnapShot: DocumentSnapshot,
        clazz: Class<T>
    ): Response<T> =
        if (documentSnapShot.exists()) {
            val data = convertObject(documentSnapShot, clazz)
            Response.Success(data)
        } else {
            Response.Empty
        }

    /**
     * Converts a Firestore query snapshot into a list of DTO objects.
     * This helper method iterates over each document in the snapshot and converts it into the specified DTO class.
     *
     * @param query The Firestore query snapshot to be converted.
     * @param clazz The class type of the DTO to map the document data into.
     * @return A list of [T] objects converted from the documents in the snapshot.
     * @throws FirebaseConversionException If the document snapshot cannot be converted into the specified DTO class.
     */
    private fun <T> convertToList(
        query: QuerySnapshot,
        clazz: Class<T>
    ): List<T> {
        return query.mapNotNull { documentSnapShot ->
            convertObject(documentSnapShot, clazz)
        }
    }

    /**
     * Converts a Firestore document snapshot into a DTO object of the specified class type.
     * This helper method maps the document data into the DTO class.
     *
     * @param document The Firestore document snapshot to be converted.
     * @param clazz The class type of the DTO to map the document data into.
     * @return The converted [T] object.
     * @throws FirebaseConversionException If the document snapshot cannot be converted into the specified DTO class.
     */
    private fun <T> convertObject(
        document: DocumentSnapshot,
        clazz: Class<T>
    ): T = document.toObject(clazz) ?: throw FirebaseConversionException(
        "DocumentSnapshot can't be converted into a DTO class: (${clazz.simpleName})."
    )

}