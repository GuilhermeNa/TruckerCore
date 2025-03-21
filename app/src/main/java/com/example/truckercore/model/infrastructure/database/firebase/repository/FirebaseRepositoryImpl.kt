package com.example.truckercore.model.infrastructure.database.firebase.repository

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.database.firebase.errors.IncompleteTaskException
import com.example.truckercore.model.infrastructure.database.firebase.util.FirebaseConverter
import com.example.truckercore.model.infrastructure.database.firebase.util.FirebaseQueryBuilder
import com.example.truckercore.model.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Transaction
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

internal class FirebaseRepositoryImpl(
    private val queryBuilder: FirebaseQueryBuilder,
    private val converter: FirebaseConverter
) : FirebaseRepository {

    override fun runTransaction(transactionOperation: (transaction: Transaction) -> Unit): Flow<Response<Unit>> =
        callbackFlow {
            queryBuilder.firestore.runTransaction { transaction ->
                transactionOperation(transaction)
            }.addOnSuccessListener {
                trySend(Response.Success(Unit))
            }.addOnFailureListener { error ->
                close(error)
            }

            awaitClose { this.cancel() }
        }

    override fun createBlankDocument(collection: Collection, documentPath: String?): DocumentReference {
        return queryBuilder.createBlankDocument(collection, documentPath)
    }

    //----------------------------------------------------------------------------------------------

    override fun create(
        collection: Collection,
        dto: Dto
    ): Flow<Response<String>> = callbackFlow {
        val docReference = queryBuilder.createBlankDocument(collection)
        val newDto = dto.initializeId(docReference.id)

        docReference.set(newDto).addOnCompleteListener { task ->
            task.exception?.let { error ->
                this.close(error)
            }

            if (task.isSuccessful) {
                trySend(Response.Success(docReference.id))
            } else {
                val error = IncompleteTaskException(
                    "Failed while creating an entity: ${dto::class.simpleName}."
                )
                this.close(error)
            }

        }

        awaitClose { this.cancel() }
    }

    override fun update(
        collection: Collection,
        dto: Dto
    ): Flow<Response<Unit>> = callbackFlow {
        val document = queryBuilder.getDocument(collection, dto.id!!)

        document.set(dto).addOnCompleteListener { task ->
            task.exception?.let { error ->
                this.close(error)
            }
            if (task.isSuccessful) this.trySend(Response.Success(Unit))
            else {
                val error = IncompleteTaskException(
                    "Failed while updating an entity ${dto::class.simpleName}.",
                )
                this.close(error)
            }
        }

        awaitClose { this.cancel() }
    }

    override fun delete(
        collection: Collection,
        id: String
    ): Flow<Response<Unit>> = callbackFlow {
        val document = queryBuilder.getDocument(collection, id)

        document.delete().addOnCompleteListener { task ->
            task.exception?.let { error ->
                this.close(error)
            }
            if (task.isSuccessful) this.trySend(Response.Success(Unit))
            else {
                val error = IncompleteTaskException(
                    "Failed while deleting an entity for id: $id."
                )
                this.close(error)
            }
        }

        awaitClose { this.cancel() }
    }

    override fun entityExists(
        collection: Collection,
        id: String
    ): Flow<Response<Unit>> = flow {
        val docReference = queryBuilder.getDocument(collection, id)
        val nDocSnap = docReference.get().await()

        nDocSnap.let { dss ->
            val result =
                if (dss.exists()) Response.Success(Unit)
                else Response.Empty
            emit(result)
        }
    }

    override fun fetchLoggedUser(userId: String, shouldStream: Boolean): Flow<Response<UserDto>> =
        when (shouldStream) {
            false -> getLoggedUser(userId)
            true -> streamLoggedUser(userId)
        }

    override fun <T : Dto> documentFetch(firebaseRequest: FirebaseRequest<T>): Flow<Response<T>> {
        return when (firebaseRequest.shouldObserve()) {
            true -> streamDocument(firebaseRequest)
            false -> getDocument(firebaseRequest)
        }
    }

    override fun <T : Dto> queryFetch(firebaseRequest: FirebaseRequest<T>): Flow<Response<List<T>>> {
        return when (firebaseRequest.shouldObserve()) {
            true -> streamQuery(firebaseRequest)
            false -> getQuery(firebaseRequest)
        }
    }

    //----------------------------------------------------------------------------------------------

    private fun getLoggedUser(userId: String) = flow {
        val docReference = queryBuilder.getDocument(Collection.USER, userId)
        val nDocSnap = docReference.get().await()

        nDocSnap.let { dss ->
            val result = converter.processDocumentSnapShot(dss, UserDto::class.java)
            emit(result)
        }
    }

    private fun streamLoggedUser(userId: String) = callbackFlow {
        val docReference = queryBuilder.getDocument(Collection.USER, userId)

        docReference.addSnapshotListener { nDocSnap, nError ->
            nError?.let { error ->
                this.close(error)
            }
            nDocSnap.let { docSnap ->
                val result = converter.processDocumentSnapShot(docSnap!!, UserDto::class.java)
                this.trySend(result)
            }
        }

        awaitClose { this.cancel() }
    }

    private fun <T : Dto> getDocument(firebaseRequest: FirebaseRequest<T>) = flow {
        val params = firebaseRequest.getDocumentParams()

        val documentRef = queryBuilder.getDocument(firebaseRequest.collection, params.id)
        val docSnap = documentRef.get().await()

        docSnap.let { dss ->
            val result = converter.processDocumentSnapShot(dss, firebaseRequest.clazz)
            emit(result)
        }
    }

    private fun <T : Dto> streamDocument(firebaseRequest: FirebaseRequest<T>) = callbackFlow {
        val params = firebaseRequest.getDocumentParams()

        val documentReference = queryBuilder.getDocument(firebaseRequest.collection, params.id)

        documentReference.addSnapshotListener { nDocSnap, nError ->
            nError?.let { error ->
                this.close(error)
            }

            nDocSnap.let { docSnap ->
                val result = converter.processDocumentSnapShot(docSnap!!, firebaseRequest.clazz)
                this.trySend(result)
            }
        }

        awaitClose { this.cancel() }
    }

    private fun <T : Dto> getQuery(firebaseRequest: FirebaseRequest<T>) = flow {
        val params = firebaseRequest.getQueryParams()

        val query = queryBuilder.getQuery(firebaseRequest.collection, *params.queries)
        val querySnapshot = query.get().await()

        querySnapshot.let { qss ->
            val result = converter.processQuerySnapShot(qss, firebaseRequest.clazz)
            emit(result)
        }
    }

    private fun <T : Dto> streamQuery(firebaseRequest: FirebaseRequest<T>) = callbackFlow {
        val params = firebaseRequest.getQueryParams()

        val query = queryBuilder.getQuery(firebaseRequest.collection, *params.queries)

        query.addSnapshotListener { nSnapShot, nError ->
            nError?.let { error ->
                this.close(error)
            }
            nSnapShot.let { snapShot ->
                val result = converter.processQuerySnapShot(snapShot!!, firebaseRequest.clazz)
                this.trySend(result)
            }
        }

        awaitClose { this.cancel() }
    }

}

