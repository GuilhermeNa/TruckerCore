package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseConversionException
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.shared.errors.UnknownErrorException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

internal class FirebaseRepositoryImpl<T : Dto>(
    private val queryBuilder: FirebaseQueryBuilder,
    private val converter: FirebaseConverter<T>,
    private val collection: Collection,
) : FirebaseRepository<T> {

    override suspend fun create(dto: T): Flow<Response<String>> = flow {
        val document = queryBuilder.newDocument(collection.getName())
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

    override suspend fun update(dto: T): Flow<Response<Unit>> = flow {
        val document = queryBuilder.getDocumentReference(collection.getName(), dto.id!!)
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

    override suspend fun delete(id: String): Flow<Response<Unit>> = flow {
        val document = queryBuilder.getDocumentReference(collection.getName(), id)
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

    override suspend fun entityExists(id: String): Flow<Response<Unit>> = flow {
        val document = queryBuilder.getDocumentReference(collection.getName(), id)
        val documentReference = document.get().await()

        val response = documentReference?.let { dss ->
            if (dss.exists()) Response.Success(Unit)
            else Response.Empty
        } ?: Response.Error(NullPointerException())

        emit(response)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    override suspend fun simpleDocumentFetch(id: String): Flow<Response<T>> = flow {
        val document = queryBuilder.getDocumentReference(collection.getName(), id)
        val documentSnapShot = document.get().await()

        val response = documentSnapShot?.let { dss ->
            converter.processDocumentSnapShot(dss)
        } ?: Response.Empty

        emit(response)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    override suspend fun simpleQueryFetch(field: Field, value: String): Flow<Response<List<T>>> =
        flow {
            val query = queryBuilder.getQuery(collection.getName(), field.name, value)
            val querySnapshot = query.get().await()

            val response = querySnapshot?.let { qss ->
                converter.processQuerySnapShot(qss)
            } ?: Response.Empty

            emit(response)

        }.catch {
            emit(handleUnexpectedError(it))
        }

    override suspend fun simpleQueryFetch(field: Field, values: List<String>): Flow<Response<List<T>>> =
        flow {
            val query = queryBuilder.getQuery(collection.getName(), field.name, values)
            val querySnapshot = query.get().await()

            val response = querySnapshot?.let { qss ->
                converter.processQuerySnapShot(qss)
            } ?: Response.Empty

            emit(response)

        }.catch {
            emit(handleUnexpectedError(it))
        }

    /**
     * Builds an error response based on the provided exception.
     *
     * This method identifies the type of exception thrown during Firebase operations
     * (e.g., conversion issues, network failures, Firestore exceptions, or authentication errors)
     * and constructs a detailed error message that will be returned to the caller.
     *
     * @param collection The name of the Firestore collection that was being accessed.
     * @param field The field that was being queried, if applicable. Default is null.
     * @param value The value of the field that caused the issue.
     * @param throwable The exception that was thrown during the Firebase operation.
     * @return A [Response.Error] containing a detailed error message and the thrown exception.
     */
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