package com.example.truckercore.infrastructure.database.firebase.interfaces

import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

internal interface FirebaseQueryBuilder {

    /**
     * Creates a new document reference in the specified Firestore collection.
     *
     * This method returns a new [DocumentReference] which can be used to perform operations on the document.
     *
     * @param collectionName The name of the Firestore collection where the new document will be created.
     * @return A new [DocumentReference] that points to the new document in the specified collection.
     */
    fun newDocument(collectionName: String): DocumentReference

    /**
     * Constructs a query to retrieve documents from the specified Firestore collection,
     * filtered by a field and a value.
     *
     * @param collectionName The name of the Firestore collection to query.
     * @param field The field to be used for filtering documents.
     * @param value The value to match the field against.
     * @return A [Query] that can be executed to retrieve documents that match the field and value criteria.
     */
    fun getQuery(collectionName: String, field: String, value: String): Query

    /**
     * Constructs a query to retrieve documents from the specified Firestore collection,
     * filtered by a field and a value.
     *
     * @param collectionName The name of the Firestore collection to query.
     * @param field The field to be used for filtering documents.
     * @param values The list of values to match the field against.
     * @return A [Query] that can be executed to retrieve documents that match the field and value criteria.
     */
    fun getQuery(collectionName: String, field: String, values: List<String>): Query

    /**
     * Builds a document reference query based on the collection name and document ID.
     *
     * @param collectionName The name of the collection where the document is located.
     * @param id The ID of the document to reference.
     * @return A [DocumentReference] instance that can be used to access the document in Firestore.
     */
    fun getDocumentReference(collectionName: String, id: String): DocumentReference

    fun getQuery(collectionName: String, querySettings: List<QuerySettings>): Query

}