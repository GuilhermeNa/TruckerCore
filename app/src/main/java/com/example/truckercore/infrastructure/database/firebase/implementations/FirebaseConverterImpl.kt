package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseConversionException
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.shared.utils.Response
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

internal class FirebaseConverterImpl<T>(private val clazz: Class<T>) : FirebaseConverter<T> {

    override fun processTask(task: Task<Void>, document: DocumentReference?): Response<*> {
        return document?.let {
            processStringTask(task, it)
        } ?: processUnitTask(task)
    }

    override fun processQuerySnapShot(querySnapShot: QuerySnapshot): Response<List<T>> =
        if (!querySnapShot.isEmpty) {
            val data = convertToList(querySnapShot)
            Response.Success(data)
        } else {
            Response.Empty
        }

    override fun processDocumentSnapShot(documentSnapShot: DocumentSnapshot): Response<T> =
        if (documentSnapShot.exists()) {
            val data = convertObject(documentSnapShot)
            Response.Success(data)
        } else {
            Response.Empty
        }

    override fun processEntityExistence(documentSnapShot: DocumentSnapshot) =
        if (documentSnapShot.exists()) Response.Success(true)
        else Response.Empty

    //----------------------------------------------------------------------------------------------

    private fun processStringTask(task: Task<Void>, document: DocumentReference): Response<String> {
        task.exception?.let {
            return Response.Error(exception = it)
        }
        return if (task.isSuccessful) {
            Response.Success(document.id)
        } else Response.Empty
    }

    private fun processUnitTask(task: Task<Void>): Response<Unit> {
        task.exception?.let {
            return Response.Error(exception = it)
        }
        return if (task.isSuccessful) {
            Response.Success(Unit)
        } else Response.Empty
    }

    // Convert a list of fireBase documents into a List<DTO>
    private fun convertToList(query: QuerySnapshot): List<T> {
        return query.mapNotNull { documentSnapShot ->
            convertObject(documentSnapShot)
        }
    }

    // Convert fireBase objects in DTO.
    private fun convertObject(document: DocumentSnapshot): T =
        document.toObject(clazz) ?: throw FirebaseConversionException(
            "DocumentSnapshot can't be converted into a DTO class: (${clazz.simpleName})."
        )

}