package com.example.truckercore.modules.fleet.shared.module.licensing.repository

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class LicensingRepositoryImpl(
    private val firebaseRepository: FirebaseRepository<LicensingDto>
) : LicensingRepository {

    override suspend fun fetchByParentId(vararg parentId: String) =
        if (parentId.size == 1) {
            firebaseRepository.simpleQueryFetch(Field.PARENT_ID, parentId.first())
        } else {
            firebaseRepository.simpleQueryFetch(Field.PARENT_ID, parentId.asList())
        }

    override suspend fun create(dto: LicensingDto) =
        firebaseRepository.create(dto)

    override suspend fun update(dto: LicensingDto) =
        firebaseRepository.update(dto)

    override suspend fun delete(id: String) =
        firebaseRepository.delete(id)

    override suspend fun entityExists(id: String) =
        firebaseRepository.entityExists(id)

    override suspend fun fetchById(id: String) =
        firebaseRepository.simpleDocumentFetch(id)

}