package com.example.truckercore.infrastructure.database.firebase.interfaces

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

/**
 * Interface for building Firestore queries.
 *
 * This interface defines the necessary methods for constructing specific queries for a Firestore collection
 * based on the provided parameters. Each method constructs a query that can be used to interact with Firestore.
 */
internal interface FirebaseQueryBuilder {

    /**
     * Builds a document reference query based on the collection name and document ID.
     *
     * @param collectionName The name of the collection where the document is located.
     * @param id The ID of the document to reference.
     * @return A [DocumentReference] instance that can be used to access the document in Firestore.
     */
    fun buildDocumentReferenceQuery(collectionName: String, id: String): DocumentReference

    /**
     * Builds a query to find documents where the "masterUid" field matches the given value.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param masterUid The value of "masterUid" to filter the documents by.
     * @return A [Query] instance configured to search for documents where "masterUid" equals the given value.
     */
    fun buildMasterUidQuery(collectionName: String, masterUid: String): Query

    /**
     * Builds a query to find documents where the "truckId" field matches the given value.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param truckId The value of "truckId" to filter the documents by.
     * @return A [Query] instance configured to search for documents where "truckId" equals the given value.
     */
    fun buildTruckIdQuery(collectionName: String, truckId: String): Query

    /**
     * Builds a query to find documents where the "employeeId" field matches the given value.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param employeeId The value of "employeeId" to filter the documents by.
     * @return A [Query] instance configured to search for documents where "employeeId" equals the given value.
     */
    fun buildEmployeeIdQuery(collectionName: String, employeeId: String): Query

    /**
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
     */
    fun buildTruckIdFilteredQuery(
        collectionName: String,
        truckId: String,
        filter: String,
        limit: Int = 1,
        direction: Query.Direction
    ): Query

    /**
     * Builds a query to find documents where the "truckId" field matches the given value
     * and the "status" field is one of the values in the `statusList`.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param truckId The value of "truckId" to filter the documents by.
     * @param statusList A list of status values to filter the documents by. Only documents whose "status"
     *                   matches one of the values in the list will be returned.
     * @return A [Query] instance configured to find documents where "truckId" equals the given value
     *         and "status" matches one of the values in `statusList`.
     */
    fun buildTruckIdAndStatusQuery(
        collectionName: String,
        truckId: String,
        statusList: List<String>
    ): Query

    /**
     * Builds a query to find documents where the "customerId" field matches the given value.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param customerId The value of "customerId" to filter the documents by.
     * @return A [Query] instance configured to find documents where "customerId" equals the given value.
     */
    fun buildCustomerIdQuery(collectionName: String, customerId: String): Query

    /**
     * Builds a query to find documents where the "travelId" field matches the given value.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param travelId The value of "travelId" to filter the documents by.
     * @return A [Query] instance configured to find documents where "travelId" equals the given value.
     */
    fun buildTravelIdQuery(collectionName: String, travelId: String): Query

    /**
     * Builds a query to find documents where the "labelId" field matches the given value.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param labelId The value of "labelId" to filter the documents by.
     * @return A [Query] instance configured to find documents where "labelId" equals the given value.
     */
    fun buildLabelIdQuery(collectionName: String, labelId: String): Query

    /**
     * Builds a query to find documents where the "freightId" field matches the given value.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param freightId The value of "freightId" to filter the documents by.
     * @return A [Query] instance configured to find documents where "freightId" equals the given value.
     */
    fun buildFreightIdQuery(collectionName: String, freightId: String): Query

    /**
     * Builds a query to find documents where the "parentId" field matches the given value.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param parentId The value of "parentId" to filter the documents by.
     * @return A [Query] configured to find documents where "parentId" equals the given value.
     */
    fun buildParentIdQuery(collectionName: String, parentId: String): Query

    /**
     * Builds a query to find documents where the "vacationId" field matches the given value.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param vacationId The value of "vacationId" to filter the documents by.
     * @return A [Query] configured to find documents where "vacationId" equals the given value.
     */
    fun buildVacationIdQuery(collectionName: String, vacationId: String): Query

    /**
     * Builds a query to find documents where the "userId" field matches the given value.
     *
     * @param collectionName The name of the collection where the documents are located.
     * @param userId The value of "userId" to filter the documents by.
     * @return A [Query] configured to find documents where "userId" equals the given value.
     */
    fun buildUserIdQuery(collectionName: String, userId: String): Query

}