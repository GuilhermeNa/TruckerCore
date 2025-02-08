package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.errors.UnsuccessfulTaskException
import com.example.truckercore.infrastructure.database.firebase.interfaces.NewFireBaseRepository
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

internal class NewFireBaseRepositoryImpl(
    private val queryBuilder: NewFirebaseQueryBuilder,
    private val converter: NewFirebaseConverter
) : NewFireBaseRepository {

    override fun create(
        collection: Collection,
        dto: Dto
    ): Flow<Response<String>> = callbackFlow {
        val document = queryBuilder.createDocument(collection)
        val newDto = dto.initializeId(document.id)

        document.set(newDto).addOnCompleteListener { task ->
            task.exception?.let { error ->
                this.close(error)
                throw error
            }
            if (task.isSuccessful) trySend(Response.Success(document.id))
            else throw UnsuccessfulTaskException(
                message = "Failed while creating an entity.",
                dto = dto,
                collection = collection
            )
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
                throw error
            }
            if (task.isSuccessful) trySend(Response.Success(Unit))
            else throw UnsuccessfulTaskException(
                message = "Failed while updating an entity.",
                dto = dto,
                collection = collection
            )
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
                throw error
            }
            if (task.isSuccessful) trySend(Response.Success(Unit))
            else throw UnsuccessfulTaskException(
                message = "Failed while deleting an entity.",
                id = id,
                collection = collection
            )
        }

        awaitClose { this.cancel() }
    }

    override fun entityExists(
        collection: Collection,
        id: String
    ): Flow<Response<Unit>> = flow {
        val document = queryBuilder.getDocument(collection, id)
        val documentSnap = document.get().await()

        documentSnap?.let { dss ->
            val result =
                if (dss.exists()) Response.Success(Unit)
                else Response.Empty
            emit(result)
        } ?: throw UnsuccessfulTaskException(
            message = "Failed while verifying an entity existence.",
            id = id,
            collection = collection
        )
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

    private fun <T : Dto> getDocument(firebaseRequest: FirebaseRequest<T>) = flow {
        val params = firebaseRequest.getDocumentParams()

        val documentRef = queryBuilder.getDocument(firebaseRequest.collection, params.id)
        val querySnapshot = documentRef.get().await()

        val response = querySnapshot?.let { qss ->
            converter.processDocumentSnapShot(qss, firebaseRequest.clazz)
        } ?: Response.Empty

        emit(response)
    }

    private fun <T : Dto> streamDocument(firebaseRequest: FirebaseRequest<T>) = callbackFlow {
        val params = firebaseRequest.getDocumentParams()

        val documentReference = queryBuilder.getDocument(firebaseRequest.collection, params.id)

        documentReference.addSnapshotListener { nDocSnap, nError ->
            nError?.let { error ->
                this.close(error)
                throw error
            }
            nDocSnap?.let { docSnap ->
                val result = converter.processDocumentSnapShot(docSnap, firebaseRequest.clazz)
                this.trySend(result)
            }
        }

        awaitClose { this.cancel() }
    }

    private fun <T : Dto> getQuery(firebaseRequest: FirebaseRequest<T>) = flow {
        val params = firebaseRequest.getQueryParams()

        val query = queryBuilder.getQuery(firebaseRequest.collection, *params.queries)
        val querySnapshot = query.get().await()

        val response = querySnapshot?.let { qss ->
            converter.processQuerySnapShot(qss, firebaseRequest.clazz)
        } ?: Response.Empty

        emit(response)
    }

    private fun <T : Dto> streamQuery(firebaseRequest: FirebaseRequest<T>) = callbackFlow {
        val params = firebaseRequest.getQueryParams()

        val query = queryBuilder.getQuery(firebaseRequest.collection, *params.queries)

        query.addSnapshotListener { nSnapShot, nError ->
            nError?.let { error ->
                this.close(error)
                throw error
            }
            nSnapShot?.let { snapShot ->
                val result = converter.processQuerySnapShot(snapShot, firebaseRequest.clazz)
                this.trySend(result)
            }
        }

        awaitClose { this.cancel() }
    }

}

