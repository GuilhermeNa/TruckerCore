package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseConversionException
import com.example.truckercore.infrastructure.database.firebase.interfaces.NewFireBaseRepository
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.shared.errors.UnknownErrorException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

internal class NewFireBaseRepositoryImpl(
    private val queryBuilder: NewFirebaseQueryBuilder,
    private val converter: NewFirebaseConverter
) : NewFireBaseRepository {

    override suspend fun create(
        collection: Collection,
        dto: Dto
    ): Flow<Response<String>> = flow {
        val document = queryBuilder.createDocument(collection.getName())
        val newDto = dto.initializeId(document.id)
        var result: Response<String>? = null

        document.set(newDto).addOnCompleteListener { task ->
            task.exception?.let { e ->
                result = Response.Error(e)
                return@addOnCompleteListener
            }
            result =
                if (task.isSuccessful) Response.Success(document.id)
                else Response.Error(UnknownErrorException("The task returned a unsuccessful response."))
        }.await()

        emit(
            result
                ?: Response.Error(
                    UninitializedPropertyAccessException("Result is not initialized.")
                )
        )

    }.catch {
        emit(handleUnexpectedError(it))
    }

    override suspend fun update(collection: Collection, dto: Dto): Flow<Response<Unit>> = flow {
        val document = queryBuilder.getDocument(collection.getName(), dto.id!!)
        var result: Response<Unit>? = null

        document.set(dto).addOnCompleteListener { task ->
            task.exception?.let {
                result = Response.Error(it)
                return@addOnCompleteListener
            }
            result =
                if (task.isSuccessful) Response.Success(Unit)
                else Response.Error(UnknownErrorException("The task returned a unsuccessful response."))
        }.await()

        emit(
            result
                ?: Response.Error(UninitializedPropertyAccessException("Result is not initialized"))
        )

    }.catch {
        emit(handleUnexpectedError(it))
    }

    override suspend fun delete(collection: Collection, id: String): Flow<Response<Unit>> = flow {
        val document = queryBuilder.getDocument(collection.getName(), id)
        var result: Response<Unit>? = null

        document.delete().addOnCompleteListener { task ->
            task.exception?.let {
                result = Response.Error(it)
                return@addOnCompleteListener
            }

            result =
                if (task.isSuccessful) Response.Success(Unit)
                else Response.Error(UnknownErrorException("The task returned a unsuccessful response."))

        }.await()

        emit(
            result
                ?: Response.Error(UninitializedPropertyAccessException("Result is not initialized"))
        )

    }.catch {
        emit(handleUnexpectedError(it))
    }

    override suspend fun entityExists(collection: Collection, id: String): Flow<Response<Unit>> =
        flow {
            val document = queryBuilder.getDocument(collection.getName(), id)
            val documentReference = document.get().await()

            val response = documentReference?.let { dss ->
                if (dss.exists()) Response.Success(Unit)
                else Response.Empty
            } ?: Response.Error(NullPointerException())

            emit(response)

        }.catch {
            emit(handleUnexpectedError(it))
        }

    override suspend fun <T : Dto> documentFetch(firebaseRequest: FirebaseRequest<T>): Flow<Response<T>> {
        return when (firebaseRequest.shouldObserve()) {
            true -> observeDocumentStream(firebaseRequest)
            false -> fetchByDocumentSingle(firebaseRequest)
        }
    }

    override suspend fun <T : Dto> queryFetch(firebaseRequest: FirebaseRequest<T>): Flow<Response<List<T>>> {
        return when (firebaseRequest.shouldObserve()) {
            true -> observeQueryStream(firebaseRequest)
            false -> fetchByQuerySingle(firebaseRequest)
        }
    }

    /*
        override suspend fun <T : Dto> documentFetch(
            collection: Collection,
            clazz: Class<T>,
            id: String
        ): Flow<Response<T>> =
            flow {
                val document = queryBuilder.getDocument(collection.getName(), id)
                val documentSnapShot = document.get().await()

                val response = documentSnapShot?.let { dss ->
                    converter.processDocumentSnapShot(dss, clazz)
                } ?: Response.Empty

                emit(response)

            }.catch {
                emit(handleUnexpectedError(it))
            }

        override suspend fun <T : Dto> queryFetch(
            collection: Collection,
            clazz: Class<T>,
            vararg settings: QuerySettings
        ): Flow<Response<List<T>>> =
            flow {
                val query = queryBuilder.getQuery(collection.getName(), *settings)
                val querySnapshot = query.get().await()

                val response = querySnapshot?.let { qss ->
                    converter.processQuerySnapShot(qss, clazz)
                } ?: Response.Empty

                emit(response)

            }.catch {
                emit(handleUnexpectedError(it))
            }
    */

    //----------------------------------------------------------------------------------------------

    private fun <T : Dto> fetchByDocumentSingle(firebaseRequest: FirebaseRequest<T>) = flow {
        if (firebaseRequest.params !is DocumentParameters) throw IllegalArgumentException(
            "Expected a DocumentParameters and received ${firebaseRequest.params.javaClass.simpleName}."
        )

        val document = queryBuilder.getDocument(
            firebaseRequest.collection.getName(),
            firebaseRequest.params.id
        )
        val documentSnapShot = document.get().await()

        val response = documentSnapShot?.let { dss ->
            converter.processDocumentSnapShot(dss, firebaseRequest.clazz)
        } ?: Response.Empty

        emit(response)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun <T : Dto> fetchByQuerySingle(firebaseRequest: FirebaseRequest<T>) = flow {
        if (firebaseRequest.params !is QueryParameters) throw IllegalArgumentException(
            "Expected a QueryParameters and received ${firebaseRequest.params.javaClass.simpleName}."
        )

        val query = queryBuilder.getQuery(
            firebaseRequest.collection.getName(),
            *firebaseRequest.params.queries
        )
        val querySnapshot = query.get().await()

        val response = querySnapshot?.let { qss ->
            converter.processQuerySnapShot(qss, firebaseRequest.clazz)
        } ?: Response.Empty

        emit(response)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun <T : Dto> observeQueryStream(firebaseRequest: FirebaseRequest<T>) = callbackFlow {
        if (firebaseRequest.params !is QueryParameters) throw IllegalArgumentException(
            "Expected a QueryParameters and received ${firebaseRequest.params.javaClass.simpleName}."
        )

        val query = queryBuilder.getQuery(
            firebaseRequest.collection.getName(),
            *firebaseRequest.params.queries
        )
        query.addSnapshotListener { nSnapShot, nError ->
            nError?.let { error ->
                //this.trySend(Response.Error(error))
                this.close(error)
                return@addSnapshotListener
            }
            nSnapShot?.let { snapShot ->
                val result = converter.processQuerySnapShot(snapShot, firebaseRequest.clazz)
                this.trySend(result)
            }
        }
        awaitClose { this.cancel() }
    }

    private fun <T : Dto> observeDocumentStream(firebaseRequest: FirebaseRequest<T>) =
        callbackFlow {
            if (firebaseRequest.params !is DocumentParameters) throw IllegalArgumentException(
                "Expected a DocumentParameters and received ${firebaseRequest.params.javaClass.simpleName}."
            )

            val query = queryBuilder.getDocument(
                firebaseRequest.collection.getName(),
                firebaseRequest.params.id
            )

            query.addSnapshotListener { nDocSnap, nError ->
                nError?.let { error ->
                    // this.trySend(Response.Error(error))
                    this.close(error)
                    return@addSnapshotListener
                }
                nDocSnap?.let { docSnap ->
                    val result = converter.processDocumentSnapShot(docSnap, firebaseRequest.clazz)
                    this.trySend(result)
                }
            }
            awaitClose { this.cancel() }
        }

    //----------------------------------------------------------------------------------------------

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        throwable as Exception
        val message = when (throwable) {
            is FirebaseConversionException -> "Error during conversion of Firestore document to DTO class."
            is FirebaseNetworkException -> "Network error. Please check your internet connection."
            is FirebaseFirestoreException -> "Firestore exception occurred. It could be due to invalid data, permissions, or query failures."
            is FirebaseAuthException -> "Authentication failed. Ensure user is authenticated properly."
            else -> "Unknown error."
        }
        logError(
            context = javaClass,
            exception = throwable,
            message = message
        )
        return Response.Error(throwable)
    }


}

