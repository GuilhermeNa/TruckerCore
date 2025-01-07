package com.example.truckercore.modules.personal_data.repositories.implementations

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.shared.utils.RepositoryErrorHandler
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

internal class PersonalDataRepositoryImpl(
    override val firebaseRepository: FirebaseRepository,
    override val queryBuilder: FirebaseQueryBuilder,
    override val converter: FirebaseConverter<PersonalDataDto>
) : PersonalDataRepository<PersonalDataDto> {

    override fun create(dto: PersonalDataDto): String =
        firebaseRepository.create(collectionName, dto)

    override fun update(dto: PersonalDataDto) {
        firebaseRepository.update(collectionName, dto)
    }

    override fun delete(id: String) {
        firebaseRepository.delete(collectionName, id)
    }

    override suspend fun fetchById(id: String): Flow<Response<PersonalDataDto>> = flow {
        val documentReference = queryBuilder.buildDocumentReferenceQuery(collectionName, id)
        val documentSnapShot = documentReference.get().await()
        val data = converter.processDocumentSnapShot(documentSnapShot)
        emit(data)
    }.catch {
        val response = RepositoryErrorHandler.handleException(it as Exception)
        emit(response)
    }

    override suspend fun fetchPersonalDataByParentId(parentId: String) = flow {
        val query = queryBuilder.buildParentIdQuery(collectionName, parentId)
        val querySnapShot = query.get().await()
        val data = converter.processQuerySnapShot(querySnapShot)
        emit(data)
    }.catch {
        val response = RepositoryErrorHandler.handleException(it as Exception)
        emit(response)
    }

}