package com.example.truckercore.modules.fleet.shared.module.licensing.repository

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.infrastructure.database.firebase.interfaces.NewFireBaseRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import org.checkerframework.checker.units.qual.A

internal class LicensingRepositoryImpl(
    private val firebaseRepository: NewFireBaseRepository,
    private val collection: Collection
) : LicensingRepository {

    override suspend fun <T : Dto> create(dto: T): Flow<Response<String>> {
        return firebaseRepository.create(collection, dto)
    }

    override suspend fun <T : Dto> update(dto: T): Flow<Response<Unit>> {
        return firebaseRepository.update(collection, dto)
    }

    override suspend fun delete(id: String): Flow<Response<Unit>> {
        return firebaseRepository.delete(collection, id)
    }

    override suspend fun entityExists(id: String): Flow<Response<Unit>> {
        return firebaseRepository.entityExists(collection, id)
    }

    override suspend fun fetchById(id: String): Flow<Response<LicensingDto>> {
        return firebaseRepository.documentFetch(collection, id, LicensingDto::class.java)
    }

    override suspend fun fetchByQuery(settings: List<QuerySettings>): Flow<Response<List<LicensingDto>>> {
        return firebaseRepository.queryFetch(collection, settings, LicensingDto::class.java)
    }


    /*
        override suspend fun fetchByParentId(vararg parentId: String) =
            if (parentId.size == 1) {
                firebaseRepository.simpleQueryFetch(Field.PARENT_ID, parentId.first())
            } else {
                firebaseRepository.simpleQueryFetch(Field.PARENT_ID, parentId.asList())
            }

        override suspend fun testeQuery(settings: List<QuerySettings>): Flow<Response<List<LicensingDto>>> {
            return firebaseRepository.queryFetch(settings)
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
            firebaseRepository.documentFetch(id)
    */

}