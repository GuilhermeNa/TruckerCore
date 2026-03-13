package com.example.truckercore.layers.data_2.remote.base

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.my_lib.expressions.toDto
import com.example.truckercore.core.my_lib.expressions.toList
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.tasks.await

private const val MULTIPLE_OBJECTS =
    "Query returned multiple objects but a single result was expected."

private const val LISTENER_ERROR =
    "An error occurred while listening for data updates."

//--------------------------------------------------------------------------------------------------
//
//--------------------------------------------------------------------------------------------------
fun <D : BaseDto> ProducerScope<D?>.observeQuery(
    dataSourceWrapper: DataSource.Query,
    clazz: Class<D>
) {
    dataSourceWrapper.value.addSnapshotListener { querySnap, error ->
        if (error != null) {
            this.close(DataException.DataSource(LISTENER_ERROR, error))
            return@addSnapshotListener
        }

        try {
            val data = querySnap.toList(clazz)
            if (data == null) {
                trySend(null)
                return@addSnapshotListener
            }
            if (data.size > 1) {
                close(DataException.DataSource(MULTIPLE_OBJECTS))
                return@addSnapshotListener
            }
            trySend(data[0])
        } catch (e: Exception) {
            close(e)
        }
    }
}

fun <D : BaseDto> ProducerScope<D?>.observeDocument(
    dataSourceWrapper: DataSource.Document,
    clazz: Class<D>
) {
    dataSourceWrapper.value.addSnapshotListener { docSnap, error ->
            if (error != null) {
                this.close(DataException.DataSource(LISTENER_ERROR, error))
                return@addSnapshotListener
            }

            try {
                val data = docSnap.toDto(clazz)
                trySend(data)
            } catch (e: Exception) {
                close(e)
            }
        }
}

suspend fun <D : BaseDto> fetchQuery(
    dataSourceWrapper: DataSource.Query,
    clazz: Class<D>
): D? {
    val query = dataSourceWrapper.value
    val querySnap = query.get().await()
    val data = querySnap.toList(clazz)
    return data?.let {
        if (it.size > 1) throw DataException.DataSource(MULTIPLE_OBJECTS)
        data.first()
    }
}

suspend fun <D : BaseDto> fetchDocument(
    dataSourceWrapper: DataSource.Document,
    clazz: Class<D>
): D? {
    val documentReference = dataSourceWrapper.value
    val documentSnap = documentReference.get().await()
    return documentSnap.toDto(clazz)
}



