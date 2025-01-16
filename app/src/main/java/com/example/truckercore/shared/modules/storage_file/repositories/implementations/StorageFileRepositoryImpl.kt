package com.example.truckercore.shared.modules.storage_file.repositories.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.shared.modules.storage_file.dtos.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.repositories.interfaces.StorageFileRepository
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class StorageFileRepositoryImpl(
    private val firebaseRepository: FirebaseRepository<StorageFileDto>,
) : StorageFileRepository {

    override suspend fun create(dto: StorageFileDto): Flow<Response<String>> =
        firebaseRepository.create(dto)

    override suspend fun update(dto: StorageFileDto): Flow<Response<Unit>> =
        firebaseRepository.update(dto)

    override suspend fun delete(id: String): Flow<Response<Unit>> =
        firebaseRepository.delete(id)

    override suspend fun entityExists(id: String) = firebaseRepository.entityExists(id)

    override suspend fun fetchById(id: String) = firebaseRepository.simpleDocumentFetch(id)

    override suspend fun fetchFilesByParentUid(parentId: String) =
        firebaseRepository.simpleQueryFetch(Field.PARENT_ID, parentId)

}

