package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
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

}