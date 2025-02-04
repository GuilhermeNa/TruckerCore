package com.example.truckercore.shared.modules.storage_file.repository

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.infrastructure.database.firebase.interfaces.NewFireBaseRepository
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class StorageFileRepositoryImpl(
    private val firebaseRepository: NewFireBaseRepository,
    private val collection: Collection
) : StorageFileRepository {

    override suspend fun <T : Dto> create(dto: T): Flow<Response<String>> =
        firebaseRepository.create(collection, dto)

    override suspend fun <T : Dto> update(dto: T): Flow<Response<Unit>> =
        firebaseRepository.update(collection, dto)

    override suspend fun delete(id: String): Flow<Response<Unit>> =
        firebaseRepository.delete(collection, id)

    override suspend fun entityExists(id: String) =
        firebaseRepository.entityExists(collection, id)

    override suspend fun fetchById(id: String) =
        firebaseRepository.documentFetch(collection, id, StorageFileDto::class.java)

    override suspend fun fetchByQuery(settings: List<QuerySettings>) =
        firebaseRepository.queryFetch(collection, settings, StorageFileDto::class.java)

}

