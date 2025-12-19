package com.example.truckercore.layers.data.data_source.data

import com.example.truckercore.core.my_lib.expressions.toDto
import com.example.truckercore.core.my_lib.expressions.toList
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.specification._contracts.Specification
import com.example.truckercore.layers.data.base.specification._contracts.SpecificationInterpreter
import com.example.truckercore.layers.data.base.specification.api_impl.wrappers.DocumentWrapper
import com.example.truckercore.layers.data.base.specification.api_impl.wrappers.QueryWrapper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Concrete implementation of [DataSource] using a backend data source (e.g., Firebase).
 *
 * This class executes actual data retrieval operations based on the specifications
 * provided by the [Specification] pattern. It handles both synchronous data fetches
 * (suspend functions) and reactive streams (Flow) for real-time updates.
 *
 * @property interpreter Responsible for converting [Specification] instances into
 * backend-specific queries or document references.
 * @property errorMapper Maps low-level exceptions into domain/application-specific exceptions.
 */
class DataSourceImpl(
    interpreter: SpecificationInterpreter,
    errorMapper: DataSourceErrorMapper
) : DataSource(interpreter, errorMapper) {

    override suspend fun <T : BaseDto> findById(spec: Specification<T>): T? {
        val docReference = getApiSpecification<DocumentWrapper>(spec).document
        val docSnap = docReference.get().await()
        return docSnap.toDto(spec.dtoClass)
    }

    override suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): List<T>? {
        val query = getApiSpecification<QueryWrapper>(spec).query
        val querySnap = query.get().await()
        return querySnap.toList(spec.dtoClass)
    }

    override fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<T?> = callbackFlow {
        val document = try {
            getApiSpecification<DocumentWrapper>(spec).document
        } catch (e: Exception) {
            this.close(e)
            return@callbackFlow
        }

        val listener = document.addSnapshotListener { docSnap, error ->
            if (error != null) {
                this.close(errorMapper(error))
                return@addSnapshotListener
            }

            runCatching { docSnap.toDto(spec.dtoClass) }
                .onSuccess { data -> this.trySend(data) }
                .onFailure { err -> this.close(err) }

        }

        awaitClose { listener.remove() }
    }

    override fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<List<T>?> = callbackFlow {
        val query = try {
            getApiSpecification<QueryWrapper>(spec).query
        } catch (e: Exception) {
            this.close(e)
            return@callbackFlow
        }

        val listener = query.addSnapshotListener { querySnap, error ->
            if (error != null) {
                this.close(errorMapper(error))
                return@addSnapshotListener
            }

            runCatching { querySnap.toList(spec.dtoClass) }
                .onSuccess { data -> this.trySend(data) }
                .onFailure { err -> this.close(err) }

        }

        awaitClose { listener.remove() }
    }

}