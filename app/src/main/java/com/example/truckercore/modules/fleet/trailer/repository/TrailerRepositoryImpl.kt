package com.example.truckercore.modules.fleet.trailer.repository

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.fleet.trailer.dto.TrailerDto

internal class TrailerRepositoryImpl(
    private val firebaseRepository: FirebaseRepository<TrailerDto>
) : TrailerRepository {

    override suspend fun fetchByTruckId(truckId: String) =
        firebaseRepository.simpleQueryFetch(Field.TRUCK_ID, truckId)

    override suspend fun create(dto: TrailerDto) =
        firebaseRepository.create(dto)

    override suspend fun update(dto: TrailerDto) =
        firebaseRepository.update(dto)

    override suspend fun delete(id: String) =
        firebaseRepository.delete(id)

    override suspend fun entityExists(id: String) =
        firebaseRepository.entityExists(id)

    override suspend fun fetchById(id: String) =
        firebaseRepository.documentFetch(id)

}