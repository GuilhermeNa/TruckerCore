package com.example.truckercore.modules.fleet.shared.module.licensing.repository

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.interfaces.NewFireBaseRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class LicensingRepositoryImpl(
    private val firebaseRepository: NewFireBaseRepository,
    private val collection: Collection
) : LicensingRepository {

    override suspend fun <T : Dto> create(dto: T): Flow<Response<String>> =
        firebaseRepository.create(collection, dto)

    override suspend fun <T : Dto> update(dto: T): Flow<Response<Unit>> =
        firebaseRepository.update(collection, dto)

    override suspend fun delete(id: String): Flow<Response<Unit>> =
        firebaseRepository.delete(collection, id)

    override suspend fun entityExists(id: String): Flow<Response<Unit>> =
        firebaseRepository.entityExists(collection, id)

    override suspend fun fetchById(id: String): Flow<Response<LicensingDto>> =
        firebaseRepository.documentFetch(collection, id, LicensingDto::class.java)

    override suspend fun fetchByQuery(vararg settings: QuerySettings) =
        firebaseRepository.queryFetch(collection, *settings, clazz = LicensingDto::class.java)

}