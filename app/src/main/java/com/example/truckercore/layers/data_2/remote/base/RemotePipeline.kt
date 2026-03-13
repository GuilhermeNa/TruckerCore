package com.example.truckercore.layers.data_2.remote.base

import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RemotePipeline {

    suspend fun <D : BaseDto> fetch(
        dataSource: DataSource,
        dataClazz: Class<D>
    ): D? = when (dataSource) {
        is DataSource.Document -> fetchDocument(dataSource, dataClazz)
        is DataSource.Query -> fetchQuery(dataSource, dataClazz)
    }

    fun <D : BaseDto> observe(
        dataSource: DataSource,
        dataClazz: Class<D>
    ): Flow<D?> = callbackFlow {
        when (dataSource) {
            is DataSource.Document -> observeDocument(dataSource, dataClazz)
            is DataSource.Query -> observeQuery(dataSource, dataClazz)
        }
    }

}