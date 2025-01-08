package com.example.truckercore.modules.business_central.repository

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto

internal class BusinessCentralRepositoryImpl(
    override val firebaseRepository: FirebaseRepository<BusinessCentralDto>
) : BusinessCentralRepository {

    override fun create(dto: BusinessCentralDto) =
        firebaseRepository.create(dto)

    override fun update(dto: BusinessCentralDto) {
        firebaseRepository.update(dto)
    }

    override fun delete(id: String) {
        firebaseRepository.delete(id)
    }

    override suspend fun entityExists(id: String) =
        firebaseRepository.entityExists(id)

    override suspend fun fetchById(id: String) =
        firebaseRepository.simpleDocumentFetch(id)

}