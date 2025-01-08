package com.example.truckercore.shared.modules.storage_file.repositories.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.shared.modules.storage_file.dtos.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.repositories.interfaces.StorageFileRepository

internal class StorageFileRepositoryImpl(
    override val firebaseRepository: FirebaseRepository<StorageFileDto>,
) : StorageFileRepository {

    override fun create(dto: StorageFileDto): String =
        firebaseRepository.create(dto)

    override fun update(dto: StorageFileDto) {
        firebaseRepository.update(dto)
    }

    override fun delete(id: String) {
        firebaseRepository.delete(id)
    }

    override suspend fun entityExists(id: String) = firebaseRepository.entityExists(id)

    override suspend fun fetchById(id: String) = firebaseRepository.simpleDocumentFetch(id)

    override suspend fun fetchFilesByParentUid(parentId: String) =
        firebaseRepository.simpleQueryFetch(Field.PARENT_ID, parentId)

}

