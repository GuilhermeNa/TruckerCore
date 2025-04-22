package com.example.truckercore.model.infrastructure.integration._data.for_api

import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.Specification

/**
 * Converts [Specification]s into backend-specific query types.
 *
 * ### Example (Firestore):
 * ```kotlin
 * class FirestoreInterpreter: DataSourceInterpreter<DocumentReference, Query>
 * ```
 *
 * @param R1 Type used for single-entity lookups.
 * @param R2 Type used for multi-entity queries.
 */
interface DataSourceSpecificationInterpreter<out R1, out R2> {

    /**
     * Converts a [Specification] into a backend-specific query object for retrieving a single item.
     *
     * This method is intended for lookups that rely on a unique identifier (e.g., document ID in Firestore).
     * It translates the generic [Specification] into a concrete query type [R1], such as a `DocumentReference`,
     * which can then be used by the backend to fetch data.
     *
     * ### Example (Firestore):
     * ```kotlin
     * val documentReference = interpreter.interpretIdSearch(spec)
     * val snapshot = documentReference.get().await()
     * val dto = snapshot.toObject(MyDto::class.java)
     * ```
     *
     * @param spec The specification containing the collection name and entity ID.
     * @return A backend-specific query object used to retrieve the item.
     *
     * @throws SpecificationException If the specification is invalid or missing required information.
     */
    fun interpretIdSearch(spec: Specification<*>): R1

    /**
     * Converts a [Specification] into a backend-specific query object for retrieving multiple items.
     *
     * This method is used when performing filtered queries — for example, retrieving a list of items that match
     * specific conditions (such as `whereEqualTo`, `orderBy`, etc.). The [Specification] provides the filters
     * and collection information, which are then translated into a query type [R2] understood by the backend.
     *
     * ### Example (Firestore):
     * ```kotlin
     * val query = interpreter.interpretFilterSearch(spec)
     * val snapshots = query.get().await()
     * val items = snapshots.toObjects(MyDto::class.java)
     * ```
     *
     * @param spec The specification containing collection name and a list of filters.
     * @return A backend-specific query object that can be used to fetch a list of matching items.
     *
     * @throws SpecificationException If any unsupported filter or invalid configuration is detected.
     */
    fun interpretFilterSearch(spec: Specification<*>): R2

}