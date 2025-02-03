package com.example.truckercore.modules.fleet.truck.repository

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.fleet.truck.dto.TruckDto

internal class TruckRepositoryImpl(
    private val firebaseRepository: FirebaseRepository<TruckDto>
) : TruckRepository {

    override suspend fun create(dto: TruckDto) =
        firebaseRepository.create(dto)

    override suspend fun update(dto: TruckDto) =
        firebaseRepository.update(dto)

    override suspend fun delete(id: String) =
        firebaseRepository.delete(id)

    override suspend fun entityExists(id: String) =
        firebaseRepository.entityExists(id)

    override suspend fun fetchById(id: String) =
        firebaseRepository.documentFetch(id)

}