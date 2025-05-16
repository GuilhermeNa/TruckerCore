package com.example.truckercore.model.infrastructure.data_source.firebase.data

import com.example.truckercore._utils.expressions.safeEmit
import com.example.truckercore._utils.expressions.toDto
import com.example.truckercore._utils.expressions.toList
import com.example.truckercore.model.infrastructure.data_source.firebase.data.api_specification.ApiDocumentReferenceSpecification
import com.example.truckercore.model.infrastructure.data_source.firebase.data.api_specification.ApiQuerySpecification
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSource
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSourceErrorMapper
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSourceSpecificationInterpreter
import com.example.truckercore.model.infrastructure.integration.data.for_api.data.contracts.ApiSpecification
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.exceptions.SpecificationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreDataSource(
    errorMapper: DataSourceErrorMapper,
    interpreter: DataSourceSpecificationInterpreter
) : DataSource(interpreter, errorMapper) {

    override suspend fun <T : BaseDto> findById(spec: Specification<T>): T? = try {
        val docReference = getApiSpecification<ApiDocumentReferenceSpecification>(spec).document
        val docSnap = docReference.get().await()
        docSnap.toDto(spec.dtoClass)
    } catch (e: Exception) {
        throw errorMapper(e, spec)
    }

    override suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): List<T>? = try {
        val query = getApiSpecification<ApiQuerySpecification>(spec).query
        val querySnap = query.get().await()
        querySnap.toList(spec.dtoClass)
    } catch (e: Exception) {
        throw errorMapper(e, spec)
    }

    override fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<T?> = callbackFlow {
        val document = try {
            getApiSpecification<ApiDocumentReferenceSpecification>(spec).document
        } catch (e: Exception) {
            this.close(errorMapper(e, spec))
            return@callbackFlow
        }

        val listener = document.addSnapshotListener { docSnap, error ->
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
        val query = try {
            getApiSpecification<ApiQuerySpecification>(spec).query
        } catch (e: Exception) {
            this.close(errorMapper(e, spec))
            return@callbackFlow
        }

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