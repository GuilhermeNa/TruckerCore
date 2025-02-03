package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseConversionException
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class NewFirebaseConverter {

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
    ): T = document.toObject(clazz) ?: throw FirebaseConversionException(
        "DocumentSnapshot can't be converted into a DTO class: (${clazz.simpleName})."
    )

}