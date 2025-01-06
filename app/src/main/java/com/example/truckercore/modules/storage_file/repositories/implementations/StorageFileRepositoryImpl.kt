package com.example.truckercore.modules.storage_file.repositories.implementations

import com.example.truckercore.modules.storage_file.repositories.interfaces.StorageFileRepository
import com.example.truckercore.modules.storage_file.dtos.StorageFileDto
import com.example.truckercore.configs.database.Collection
import com.example.truckercore.infrastructure.database.firebase.Repository
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

internal class StorageFileRepositoryImpl(
    override val firebaseRepository: FirebaseRepository,
    override val queryBuilder: FirebaseQueryBuilder,
    override val converter: FirebaseConverter<StorageFileDto>,
    override val collectionName: String = Collection.FILE
) : Repository<StorageFileDto>(firebaseRepository, queryBuilder, converter, collectionName),
    StorageFileRepository {

    override fun createFile(dto: StorageFileDto): String =
        firebaseRepository.create(collectionName, dto)

    override fun updateFile(dto: StorageFileDto) {
        firebaseRepository.update(collectionName, dto)
    }

    override fun deleteFile(id: String) {
        firebaseRepository.delete(collectionName, id)
    }
    override suspend fun fetchFileById(id: String) = flow {
        val documentReference = queryBuilder.buildDocumentReferenceQuery(collectionName, id)
        val documentSnapShot = documentReference.get().await()
        val data = converter.processDocumentSnapShot(documentSnapShot)
        emit(data)
    }.catch {
        emit(handleErrors(it as Exception))
    }

    override suspend fun fetchFilesByParentUid(parentId: String) = flow {
        val query = queryBuilder.buildParentIdQuery(collectionName, parentId)
        val querySnapshot = query.get().await()
        val data = converter.processQuerySnapShot(querySnapshot)
        emit(data)
    }.catch {
        emit(handleErrors(it as Exception))
    }

}