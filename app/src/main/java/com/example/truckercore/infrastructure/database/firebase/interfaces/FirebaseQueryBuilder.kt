package com.example.truckercore.infrastructure.database.firebase.interfaces

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


    fun getQuery(collectionName: String, field: String, values: List<String>): Query

    /**
     * Builds a document reference query based on the collection name and document ID.
     *
     * @param collectionName The name of the collection where the document is located.
     * @param id The ID of the document to reference.
     * @return A [DocumentReference] instance that can be used to access the document in Firestore.
     */
    fun getDocumentReference(collectionName: String, id: String): DocumentReference

}

/*    *//**
     * Builds a query to find documents where the "truckId" field matches the given value,
     * ordered by a specific field, and limited to a maximum number of results.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param truckId The value of "truckId" to filter the documents by.
     * @param filter The name of the field to order the results by.
     * @param limit The maximum number of documents to return. The default is 1.
     * @param direction The direction of the sorting. It can be either [Query.Direction.ASCENDING] or [Query.Direction.DESCENDING].
     * @return A [Query] instance configured to find documents where "truckId" equals the given value,
     *         ordered by `filter`, and limited to `limit`.
     *//*
    fun buildTruckIdFilteredQuery(
        collectionName: String,
        truckId: String,
        filter: String,
        limit: Int = 1,
        direction: Query.Direction
    ): Query

    *//**
     * Builds a query to find documents where the "truckId" field matches the given value
     * and the "status" field is one of the values in the `statusList`.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param truckId The value of "truckId" to filter the documents by.
     * @param statusList A list of status values to filter the documents by. Only documents whose "status"
     *                   matches one of the values in the list will be returned.
     * @return A [Query] instance configured to find documents where "truckId" equals the given value
     *         and "status" matches one of the values in `statusList`.
     *//*
    fun buildTruckIdAndStatusQuery(
        collectionName: String,
        truckId: String,
        statusList: List<String>
    ): Query*/
