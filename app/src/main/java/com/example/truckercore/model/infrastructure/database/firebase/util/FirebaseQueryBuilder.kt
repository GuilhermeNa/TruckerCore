package com.example.truckercore.model.infrastructure.database.firebase.util

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.shared.enums.QueryType
import com.example.truckercore.model.shared.utils.parameters.QuerySettings
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * Helper class that facilitates the creation and management of Firestore queries and document references.
 * It provides methods to create new Firestore document references, retrieve existing documents, and
 * build Firestore queries by applying various query settings (e.g., filters).
 * @property firestore The Firestore instance that is used to interact with the Firestore database.
 */
internal class FirebaseQueryBuilder(val firestore: FirebaseFirestore) {

    /**
     * Creates a new Firestore document reference in the specified collection.
     * This method returns a new [DocumentReference], which can be used to perform various operations
     * on the document such as set, update, and delete. If no [documentPath] is provided,
     * a new document ID will be automatically generated.
     *
     * @param collectionRef The [Collection] enum value representing the Firestore collection where
     *                      the new document will be created.
     * @param documentPath The optional document ID. If provided, the document will be created
     *                     with this specific ID. If not provided, a new document with an automatically
     *                     generated ID will be created.
     * @return A [DocumentReference] pointing to the newly created document in the specified collection.
     */
    fun createBlankDocument(
        collectionRef: Collection,
        documentPath: String? = null
    ): DocumentReference {
        return documentPath?.let { path ->
            collection(collectionRef).document(path)
        } ?: collection(collectionRef).document()
    }

    /**
     * Fetches a Firestore document reference based on the collection name and document ID.
     * The [DocumentReference] allows for operations on the document (e.g., retrieve, update, delete).
     *
     * @param collectionRef The name of the Firestore collection where the document is located.
     * @param id The ID of the document to reference.
     * @return A [DocumentReference] instance that can be used to access the document in Firestore.
     */
    fun getDocument(collectionRef: Collection, id: String) = collection(collectionRef).document(id)

    /**
     * Constructs a Firestore query based on the collection name and a list of query settings.
     * The query will be built by applying each setting sequentially.
     *
     * @param collectionRef The name of the Firestore collection to query.
     * @param querySettings The list of query settings to apply to the query (e.g., filters).
     * @return A [Query] that can be executed to retrieve documents based on the provided settings.
     */
    fun getQuery(collectionRef: Collection, vararg querySettings: QuerySettings): Query {
        var query: Query = collection(collectionRef)

        querySettings.forEach { settings ->
            query = applyQuery(query, settings)
        }

        return query
    }

    /**
     * Applies a query filter based on the given [QuerySettings].
     * This method builds the actual query based on the filter type and value.
     *
     * @param query The current query to which the new filter will be applied.
     * @param settings The query settings containing the field, filter type, and value to apply.
     * @return The modified [Query] with the applied filter.
     */
    private fun applyQuery(query: Query, settings: QuerySettings): Query =
        when (settings.type) {
            QueryType.WHERE_EQUALS -> query.whereEqualTo(settings.field.getName(), settings.value)
            QueryType.WHERE_IN -> query.whereIn(settings.field.getName(), settings.value as List<*>)
        }

    // Private helper function to access Firestore collection by name
    private fun collection(collectionRef: Collection) =
        firestore.collection(collectionRef.getName())

    private fun teste() {
        firestore.collection("coleção A").document("já tenho um id")
    }
}