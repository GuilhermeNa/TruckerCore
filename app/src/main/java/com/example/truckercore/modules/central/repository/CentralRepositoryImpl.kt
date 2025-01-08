package com.example.truckercore.modules.central.repository

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.central.dto.CentralDto

internal class CentralRepositoryImpl(
    override val firebaseRepository: FirebaseRepository<CentralDto>
) : CentralRepository {

    override fun create(dto: CentralDto) =
        firebaseRepository.create(dto)

    override fun update(dto: CentralDto) {
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