package com.example.truckercore.infrastructure.database.firebase.interfaces

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

interface NewFireBaseRepository {

    suspend fun create(collection: Collection, dto: Dto): Flow<Response<String>>

    suspend fun update(collection: Collection, dto: Dto): Flow<Response<Unit>>

    suspend fun delete(collection: Collection, id: String): Flow<Response<Unit>>

    suspend fun entityExists(collection: Collection, id: String): Flow<Response<Unit>>

    suspend fun <T : Dto> documentFetch(
        collection: Collection,
        id: String,
        clazz: Class<T>
    ): Flow<Response<T>>

    suspend fun <T : Dto> queryFetch(
        collection: Collection,
        settings: List<QuerySettings>,
        clazz: Class<T>
    ): Flow<Response<List<T>>>

}