package com.example.truckercore.shared.modules.personal_data.repository

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class PersonalDataRepositoryImpl(
    private val firebaseRepository: FirebaseRepository<PersonalDataDto>
) : PersonalDataRepository {

    override suspend fun create(dto: PersonalDataDto): Flow<Response<String>> =
        firebaseRepository.create(dto)

    override suspend fun update(dto: PersonalDataDto): Flow<Response<Unit>> =
        firebaseRepository.update(dto)

    override suspend fun delete(id: String): Flow<Response<Unit>> =
        firebaseRepository.delete(id)

    override suspend fun entityExists(id: String) =
        firebaseRepository.entityExists(id)

    override suspend fun fetchById(id: String): Flow<Response<PersonalDataDto>> =
        firebaseRepository.documentFetch(id)

    override suspend fun fetchByParentId(parentId: String): Flow<Response<List<PersonalDataDto>>> =
        firebaseRepository.simpleQueryFetch(Field.PARENT_ID, parentId)

}