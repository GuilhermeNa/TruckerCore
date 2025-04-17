package com.example.truckercore.model.infrastructure.data_source.firebase._firestore

import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.FirebaseMappingException
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class FirestoreMapper {

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

    fun <T> mapDocument(
        documentSnapShot: DocumentSnapshot,
        clazz: Class<T>
    ): Response<T> =
        if (documentSnapShot.exists()) {
            val data = convertObject(documentSnapShot, clazz)
            Response.Success(data)
        } else {
            Response.Empty
        }


    private fun <T> convertToList(
        query: QuerySnapshot,
        clazz: Class<T>
    ): List<T> {
        return query.mapNotNull { documentSnapShot ->
            convertObject(documentSnapShot, clazz)
        }
    }

    private fun <T> convertObject(
        document: DocumentSnapshot,
        clazz: Class<T>
    ): T = document.toObject(clazz) ?: throw FirebaseMappingException(
        "DocumentSnapshot can't be converted into a DTO class: (${clazz.simpleName})."
    )

}