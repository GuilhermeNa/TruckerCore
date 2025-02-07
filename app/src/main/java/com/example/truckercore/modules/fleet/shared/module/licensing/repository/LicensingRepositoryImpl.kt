package com.example.truckercore.modules.fleet.shared.module.licensing.repository

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.interfaces.NewFireBaseRepository
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.shared.abstractions.Repository
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.SearchParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class LicensingRepositoryImpl(
    private val firebaseRepository: NewFireBaseRepository,
    private val collection: Collection
) : Repository(), LicensingRepository {

    override suspend fun <T : Dto> create(dto: T): Flow<Response<String>> =
        firebaseRepository.create(collection, dto)

    override suspend fun <T : Dto> update(dto: T): Flow<Response<Unit>> =
        firebaseRepository.update(collection, dto)

    override suspend fun delete(id: String): Flow<Response<Unit>> =
        firebaseRepository.delete(collection, id)

    override suspend fun entityExists(id: String): Flow<Response<Unit>> =
        firebaseRepository.entityExists(collection, id)

    override suspend fun fetchByDocument(params: DocumentParameters): Flow<Response<LicensingDto>> =
        firebaseRepository.documentFetch(createFirestoreRequest(params))

    override suspend fun fetchByQuery(params: QueryParameters): Flow<Response<List<LicensingDto>>> =
        firebaseRepository.queryFetch(createFirestoreRequest(params))

    override fun createFirestoreRequest(params: SearchParameters) =
        FirebaseRequest.create(LicensingDto::class.java)
            .setCollection(collection)
            .setParams(params)
            .build()

}