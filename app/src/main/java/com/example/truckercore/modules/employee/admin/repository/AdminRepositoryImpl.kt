package com.example.truckercore.modules.employee.admin.repository

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.employee.admin.dto.AdminDto
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class AdminRepositoryImpl(
    private val firebaseRepository: FirebaseRepository<AdminDto>
) : AdminRepository {

    override suspend fun create(dto: AdminDto): Flow<Response<String>> =
        firebaseRepository.create(dto)

    override suspend fun update(dto: AdminDto): Flow<Response<Unit>> =
        firebaseRepository.update(dto)

    override suspend fun delete(id: String): Flow<Response<Unit>> =
        firebaseRepository.delete(id)

    override suspend fun entityExists(id: String): Flow<Response<Unit>> =
        firebaseRepository.entityExists(id)

    override suspend fun fetchById(id: String): Flow<Response<AdminDto>> =
        firebaseRepository.simpleDocumentFetch(id)

}