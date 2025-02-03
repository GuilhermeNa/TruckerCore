package com.example.truckercore.shared.interfaces

import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

interface NewRepository {

    suspend fun <T : Dto> create(dto: T): Flow<Response<String>>

    suspend fun <T : Dto> update(dto: T): Flow<Response<Unit>>

    suspend fun delete(id: String): Flow<Response<Unit>>

    suspend fun entityExists(id: String): Flow<Response<Unit>>

    suspend fun fetchById(id: String): Flow<Response<*>>

    suspend fun fetchByQuery(settings: List<QuerySettings>): Flow<Response<List<*>>>

}