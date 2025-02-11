package com.example.truckercore.modules.employee.driver.repository

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.interfaces.NewFireBaseRepository
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.shared.abstractions.Repository
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.SearchParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class DriverRepositoryImpl(
    private val firebaseRepository: NewFireBaseRepository,
    private val collection: Collection
) : Repository(), DriverRepository {

    override fun createFirestoreRequest(params: SearchParameters) =
        FirebaseRequest.create(DriverDto::class.java)
            .setCollection(collection)
            .setParams(params)
            .build()

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<DriverDto>> =
        firebaseRepository.documentFetch(createFirestoreRequest(documentParams))

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<DriverDto>>> =
        firebaseRepository.queryFetch(createFirestoreRequest(queryParams))

    override fun <T : Dto> create(dto: T): Flow<Response<String>> =
        firebaseRepository.create(collection, dto)

    override fun <T : Dto> update(dto: T): Flow<Response<Unit>> =
        firebaseRepository.update(collection, dto)

    override fun delete(id: String): Flow<Response<Unit>> =
        firebaseRepository.delete(collection, id)

    override fun entityExists(id: String): Flow<Response<Unit>> =
        firebaseRepository.entityExists(collection, id)

}