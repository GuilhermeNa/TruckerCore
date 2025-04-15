package com.example.truckercore.model.modules.person.employee.driver.repository

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseRequest
import com.example.truckercore.model.modules.person.employee.driver.dto.DriverDto
import com.example.truckercore.model.shared.abstractions.Repository
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.parameters.SearchParameters
import kotlinx.coroutines.flow.Flow

internal class DriverRepositoryImpl(
    private val firebaseRepository: FirebaseRepository,
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