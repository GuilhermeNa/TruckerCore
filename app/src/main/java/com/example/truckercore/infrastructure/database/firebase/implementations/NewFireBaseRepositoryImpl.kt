package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseConversionException
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.NewFireBaseRepository
import com.example.truckercore.shared.errors.UnknownErrorException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.Flow
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

    override suspend fun <T : Dto> documentFetch(
        collection: Collection,
        id: String,
        clazz: Class<T>
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
        vararg settings: QuerySettings,
        clazz: Class<T>
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

