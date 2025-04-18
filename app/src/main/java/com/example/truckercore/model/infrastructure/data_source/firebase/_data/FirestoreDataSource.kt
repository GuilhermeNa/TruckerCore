package com.example.truckercore.model.infrastructure.data_source.firebase._data

import com.example.truckercore.model.infrastructure.data_source.firebase.expressions.safeEmit
import com.example.truckercore.model.infrastructure.data_source.firebase.expressions.toDto
import com.example.truckercore.model.infrastructure.data_source.firebase.expressions.toList
import com.example.truckercore.model.infrastructure.integration._data.for_api.DataSource
import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.Specification
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreDataSource(
    errorMapper: FirestoreErrorMapper,
    interpreter: FirestoreInterpreter
) : DataSource(interpreter, errorMapper) {

    override suspend fun <T : BaseDto> findById(spec: Specification<T>): T? = try {
        val docReference = interpreter.interpretIdSearch(spec)
        val docSnap = docReference.get().await()
        docSnap.toDto(spec.dtoClass)
    } catch (e: Exception) {
        throw errorMapper(e, spec)
    }

    override suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): List<T>? = try {
        val query = interpreter.interpretFilterSearch(spec) as Query
        val querySnap = query.get().await()
        querySnap.toList(spec.dtoClass)
    } catch (e: Exception) {
        throw errorMapper(e, spec)
    }

    override fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<T?> = callbackFlow {
        val docReference = interpreter.interpretIdSearch(spec) as DocumentReference

        val listener = docReference.addSnapshotListener { docSnap, error ->
            if (error != null) {
                this.close(errorMapper(error, spec))
                return@addSnapshotListener
            }
            safeEmit(
                block = { docSnap.toDto(spec.dtoClass) },
                error = { errorMapper(it, spec) }
            )
        }

        awaitClose { listener.remove() }
    }

    override fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<List<T>?> = callbackFlow {
        val query = interpreter.interpretFilterSearch(spec) as Query

        val listener = query.addSnapshotListener { querySnap, error ->
            if (error != null) {
                this.close(errorMapper(error, spec))
                return@addSnapshotListener
            }
            safeEmit(
                block = { querySnap.toList(spec.dtoClass) },
                error = { errorMapper(it, spec) }
            )
        }

        awaitClose { listener.remove() }
    }

}