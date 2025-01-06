package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.infrastructure.database.firebase.exceptions.FirebaseConversionException
import com.example.truckercore.infrastructure.database.firebase.exceptions.NullDocumentSnapShotException
import com.example.truckercore.infrastructure.database.firebase.exceptions.NullQuerySnapShotException
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.shared.utils.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

internal class FirebaseConverterImpl<T>(private val clazz: Class<T>) : FirebaseConverter<T> {

    override fun processQuerySnapShot(nQuerySnapShot: QuerySnapshot?): Response<List<T>> {
        return nQuerySnapShot?.let { qsn ->
            if (!qsn.isEmpty) {
                val data = convertToList(qsn)
                Response.Success(data)
            } else {
                Response.Empty
            }
        } ?: throw NullQuerySnapShotException(
            "The querySnapShot received from" +
                    " firestore is null and cannot be converted"
        )
    }

    override fun processSingleQuerySnapShotItem(nQuerySnapShot: QuerySnapshot?): Response<T> {
        return nQuerySnapShot?.let { qsn ->
            if (!qsn.isEmpty) {
                val data = convertToList(qsn).first()
                Response.Success(data)
            } else {
                Response.Empty
            }
        } ?: throw NullQuerySnapShotException(
            "The querySnapShot received from" +
                    " firestore is null and cannot be converted"
        )
    }

    override fun processDocumentSnapShot(documentSnapShot: DocumentSnapshot?): Response<T> {
        return documentSnapShot?.let { dss ->
            if (dss.exists()) {
                val data = convertObject(dss)
                Response.Success(data)
            } else {
                Response.Empty
            }
        } ?: throw NullDocumentSnapShotException(
            "The documentSnapShot received from" +
                    " firestore is null and cannot be converted."
        )
    }

    //----------------------------------------------------------------------------------------------

    // Convert a list of fireBase documents into a List<DTO>
    private fun convertToList(query: QuerySnapshot): List<T> =
        query.toObjects(clazz)

    // Convert fireBase objects in DTO.
    private fun convertObject(document: DocumentSnapshot): T =
        document.toObject(clazz) ?: throw FirebaseConversionException(
            "Error while converting firebase document to the specified " +
                    "DTO class: (${clazz.simpleName}) and document: ($document)"
        )

}