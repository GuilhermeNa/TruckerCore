package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

internal class FirebaseQueryBuilderImpl(private val firestore: FirebaseFirestore) :
    FirebaseQueryBuilder {

    private fun collection(collectionName: String) = firestore.collection(collectionName)

    override fun newDocument(collectionName: String) =
        collection(collectionName).document()

    override fun getDocumentReference(collectionName: String, id: String) =
        collection(collectionName).document(id)

    override fun getQuery(collectionName: String, field: String, value: String): Query =
        firestore.collection(collectionName).whereEqualTo(field, value)

    override fun getQuery(collectionName: String, field: String, values: List<String>) =
        firestore.collection(collectionName).whereIn(field, values)

    override fun getQuery(collectionName: String, querySettings: List<QuerySettings>): Query {
        var query: Query = collection(collectionName)

        querySettings.forEach { settings ->
            query = applyQuery(query, settings)
        }

        return query
    }

    private fun applyQuery(query: Query, settings: QuerySettings): Query =
        when (settings.type) {
            QueryType.WHERE_EQUALS -> query.whereEqualTo(settings.field.getName(), settings.value)
            QueryType.WHERE_IN -> query.whereIn(settings.field.getName(), settings.value as List<*>)
        }

}
