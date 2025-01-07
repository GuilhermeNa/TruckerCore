package com.example.truckercore.shared.modules.storage_file.repositories.implementations

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.shared.modules.storage_file.dtos.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.repositories.interfaces.StorageFileRepository
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    override suspend fun entityExists(id: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchById(id: String): Flow<Response<StorageFileDto>> = flow {
        TODO()
    }

    override suspend fun fetchFilesByParentUid(parentId: String): Flow<Response<List<StorageFileDto>>> =
        flow {
            TODO()
        }

}

