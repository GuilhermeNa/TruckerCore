package com.example.truckercore.shared.modules.personal_data.repositories.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.repositories.interfaces.PersonalDataRepository
import com.example.truckercore.shared.utils.Response
import kotlinx.coroutines.flow.Flow

internal class PersonalDataRepositoryImpl(
    override val firebaseRepository: FirebaseRepository<PersonalDataDto>
) : PersonalDataRepository {

    override fun create(dto: PersonalDataDto): String =
        firebaseRepository.create(dto)

    override fun update(dto: PersonalDataDto) {
        firebaseRepository.update(dto)
    }

    override fun delete(id: String) =
        firebaseRepository.delete(id)

    override suspend fun entityExists(id: String) =
        firebaseRepository.entityExists(id)

    override suspend fun fetchById(id: String): Flow<Response<PersonalDataDto>> =
        firebaseRepository.simpleDocumentFetch(id)

    override suspend fun fetchPersonalDataByParentId(parentId: String) =
        firebaseRepository.simpleQueryFetch(Field.PARENT_ID, parentId)

}