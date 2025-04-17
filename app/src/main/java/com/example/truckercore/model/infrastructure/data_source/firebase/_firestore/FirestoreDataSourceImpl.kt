package com.example.truckercore.model.infrastructure.data_source.firebase._firestore

import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.example.truckercore.model.infrastructure.data_source.firebase.expressions.toDto
import com.example.truckercore.model.infrastructure.data_source.firebase.expressions.toList
import com.example.truckercore.model.infrastructure.integration.specification.Specification
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Transaction
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreDataSourceImpl(
    private val firestore: FirebaseFirestore
) : FirestoreDataSource {

    private val provider = ReferenceProvider()

    override suspend fun <T : BaseDto> findOneBy(spec: Specification<T>): T {
        val docReference = provider.documentReference(spec)
        val docSnap = docReference.get().await()
        return docSnap.toDto(spec.dtoClass)
    }

    override suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): List<T> {
        val query = provider.query(spec)
        val querySnap = query.get().await()
        return querySnap.toList(spec.dtoClass)
    }

    override fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<T> = callbackFlow {
        val docReference = provider.documentReference(spec)

        val listener = docReference.addSnapshotListener { docSnap, error ->
            error?.let { e ->
                this.close(e)
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
                this.close(e)
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

}