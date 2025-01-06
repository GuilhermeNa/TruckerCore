package com.example.truckercore.modules.storage_file.repositories.implementations

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.storage_file.dtos.StorageFileDto
import com.example.truckercore.modules.storage_file.repositories.interfaces.StorageFileRepository
import com.example.truckercore.shared.utils.RepositoryErrorHandler
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

internal class StorageFileRepositoryImpl(
    override val firebaseRepository: FirebaseRepository,
    override val queryBuilder: FirebaseQueryBuilder,
    override val converter: FirebaseConverter<StorageFileDto>
) : StorageFileRepository<StorageFileDto> {

    override fun create(dto: StorageFileDto): String =
        firebaseRepository.create(collectionName, dto)

    override fun update(dto: StorageFileDto) {
        firebaseRepository.update(collectionName, dto)
    }

    override fun delete(id: String) {
        firebaseRepository.delete(collectionName, id)
    }

    override suspend fun fetchById(id: String): Flow<Response<StorageFileDto>> = flow {
        val documentReference = queryBuilder.buildDocumentReferenceQuery(collectionName, id)
        val documentSnapShot = documentReference.get().await()
        val data = converter.processDocumentSnapShot(documentSnapShot)
        emit(data)
    }.catch {
        val response = RepositoryErrorHandler.handleException(it as Exception)
        emit(response)
    }

    override suspend fun fetchFilesByParentUid(parentId: String) = flow {
        val query = queryBuilder.buildParentIdQuery(collectionName, parentId)
        val querySnapshot = query.get().await()
        val data = converter.processQuerySnapShot(querySnapshot)
        emit(data)
    }.catch {
        val response = RepositoryErrorHandler.handleException(it as Exception)
        emit(response)
    }

}

