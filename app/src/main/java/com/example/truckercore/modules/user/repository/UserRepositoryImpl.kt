package com.example.truckercore.modules.user.repository

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.user.dto.UserDto

internal class UserRepositoryImpl(
    private val firebaseRepository: FirebaseRepository<UserDto>
) : UserRepository {

    override suspend fun create(dto: UserDto) =
        firebaseRepository.create(dto)

    override suspend fun update(dto: UserDto) =
        firebaseRepository.update(dto)

    override suspend fun delete(id: String) =
        firebaseRepository.delete(id)

    override suspend fun entityExists(id: String) =
        firebaseRepository.entityExists(id)

    override suspend fun fetchById(id: String) =
        firebaseRepository.documentFetch(id)

}