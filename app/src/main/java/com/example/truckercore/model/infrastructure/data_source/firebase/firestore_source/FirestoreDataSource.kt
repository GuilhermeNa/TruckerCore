package com.example.truckercore.model.infrastructure.data_source.firebase.firestore_source

import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.example.truckercore.model.infrastructure.data_source.firebase.expressions.toDto
import com.example.truckercore.model.infrastructure.data_source.firebase.expressions.toList
import com.example.truckercore.model.infrastructure.integration.exceptions.generic_ex.NetworkException
import com.example.truckercore.model.infrastructure.integration.exceptions.generic_ex.UnknownException
import com.example.truckercore.model.infrastructure.integration.source_data.DataSource
import com.example.truckercore.model.infrastructure.integration.source_data.exceptions.DataSourceMappingException
import com.example.truckercore.model.infrastructure.integration.source_data.specification.Specification
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreDataSource(
    private val firestore: FirebaseFirestore
) : DataSource {

    private val provider = ReferenceProvider()
    private val errorHandler = ErrorHandler()

    override suspend fun <T : BaseDto> findOneBy(spec: Specification<T>): T {
        return try {
            val docReference = provider.documentReference(spec)
            val docSnap = docReference.get().await()
            docSnap.toDto(spec.dtoClass)
        } catch (e: Exception) {
            throw errorHandler(e)
        }
    }

    override suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): List<T> {
        return try {
            val query = provider.query(spec)
            val querySnap = query.get().await()
             querySnap.toList(spec.dtoClass)
        } catch (e: Exception) {
            throw errorHandler(e)
        }
    }

    override fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<T> = callbackFlow {
        val docReference = provider.documentReference(spec)

        val listener = docReference.addSnapshotListener { docSnap, error ->
            error?.let { e ->
                this.close(errorHandler(e))
                return@addSnapshotListener
            }

            if (docSnap == null) {
                this.close(IncompleteTaskException())
                return@addSnapshotListener
            }

            val dt = docSnap.toDto(spec.dtoClass)
            this.trySend(dt)
        }

        awaitClose { listener.remove() }
    }

    override fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<List<T>> = callbackFlow {
        val query = provider.query(spec)

        val listener = query.addSnapshotListener { querySnap, error ->
            error?.let { e ->
                this.close(errorHandler(e))
                return@addSnapshotListener
            }

            if (querySnap == null) {
                this.close(IncompleteTaskException())
                return@addSnapshotListener
            }

            val dt = querySnap.toList(spec.dtoClass)
            this.trySend(dt)
        }

        awaitClose { listener.remove() }
    }

    //----------------------------------------------------------------------------------------------
    // Helper Class
    //----------------------------------------------------------------------------------------------
    private inner class ReferenceProvider {

        fun <T : BaseDto> documentReference(spec: Specification<T>): DocumentReference {
            val collectionRef = firestore.collection(spec.collectionName)
            return spec.toDocumentReference(collectionRef)
        }

        fun <T : BaseDto> query(spec: Specification<T>): Query {
            val baseQuery = firestore.collection(spec.collectionName)
            return spec.toQuery(baseQuery)
        }

    }

    private class ErrorHandler {

        operator fun invoke(e: Throwable? = null): Exception {
            return when(e) {
                is FirebaseNetworkException -> NetworkException("", e)
                is DataSourceMappingException ->
                else -> UnknownException("", e)
            }
        }

    }

}