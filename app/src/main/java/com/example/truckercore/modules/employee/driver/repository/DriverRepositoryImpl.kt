package com.example.truckercore.modules.employee.driver.repository

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.shared.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class DriverRepositoryImpl(
    private val firebaseRepository: FirebaseRepository<DriverDto>
) : DriverRepository {

    override suspend fun create(dto: DriverDto): Flow<Response<String>> =
        firebaseRepository.create(dto)

    override suspend fun update(dto: DriverDto): Flow<Response<Unit>> =
        firebaseRepository.update(dto)

    override suspend fun delete(id: String): Flow<Response<Unit>> =
        firebaseRepository.delete(id)

    override suspend fun entityExists(id: String): Flow<Response<Boolean>> =
        firebaseRepository.entityExists(id)

    override suspend fun fetchById(id: String): Flow<Response<DriverDto>> =
        firebaseRepository.simpleDocumentFetch(id)

}