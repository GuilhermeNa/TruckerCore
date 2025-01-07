package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseRepositoryErrorHandler
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

internal class FirebaseRepositoryImpl<T : Dto>(
    private val queryBuilder: FirebaseQueryBuilder,
    private val converter: FirebaseConverter<T>,
    private val collection: Collection,
) : FirebaseRepository<T> {

    override fun create(dto: T): String {
        val document = queryBuilder.newDocument(collection.getName())
        val newDto = dto.initializeId(document.id)
        document.set(newDto)
        return document.id
    }

    override fun update(dto: T) {
        val document = queryBuilder.getDocumentReference(collection.getName(), dto.id!!)
        document.set(dto)
    }

    override fun delete(id: String) {
        queryBuilder.getDocumentReference(collection.getName(), id).delete()
    }

    override suspend fun entityExists(id: String): Flow<Response<Boolean>> = flow {
        val document = queryBuilder.getDocumentReference(collection.getName(), id)
        val documentReference = document.get().await()

        val response = documentReference?.let { dss ->
            converter.processEntityExistence(dss)
        } ?: Response.Empty

        emit(response)

    }.catch { t ->
        val errorResponse = FirebaseRepositoryErrorHandler.buildErrorResponse(
            collection = collection, value = id, throwable = t
        )
        emit(errorResponse)
    }

    override suspend fun simpleDocumentFetch(id: String): Flow<Response<T>> = flow {
        val document = queryBuilder.getDocumentReference(collection.getName(), id)
        val documentSnapShot = document.get().await()

        val response = documentSnapShot?.let { dss ->
            converter.processDocumentSnapShot(dss)
        } ?: Response.Empty

        emit(response)
    }.catch { t ->
        val errorResponse = FirebaseRepositoryErrorHandler.buildErrorResponse(
            collection = collection, value = id, throwable = t
        )
        emit(errorResponse)
    }

    override suspend fun simpleQueryFetch(field: Field, value: String): Flow<Response<List<T>>> =
        flow {
            val query = queryBuilder.getQuery(collection.getName(), field.name, value)
            val querySnapshot = query.get().await()

            val response = querySnapshot?.let { qss ->
                converter.processQuerySnapShot(qss)
            } ?: Response.Empty

            emit(response)

        }.catch { t ->
            val errorResponse = FirebaseRepositoryErrorHandler.buildErrorResponse(
                collection = collection, field = field, value = value, throwable = t
            )
            emit(errorResponse)
        }

}