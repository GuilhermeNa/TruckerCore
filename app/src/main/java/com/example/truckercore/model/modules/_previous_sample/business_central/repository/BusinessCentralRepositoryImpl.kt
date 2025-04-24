/*
package com.example.truckercore.model.modules._previous_sample.business_central.repository

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseRequest
import com.example.truckercore.model.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.parameters.SearchParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

internal class BusinessCentralRepositoryImpl(
    private val firebaseRepository: FirebaseRepository,
    private val collection: Collection
) : Repository(), BusinessCentralRepository {

    override fun <T : Dto> create(dto: T): Flow<AppResponse<String>> =
        firebaseRepository.create(collection, dto)

    override fun <T : Dto> update(dto: T): Flow<AppResponse<Unit>> =
        firebaseRepository.update(collection, dto)

    override fun delete(id: String): Flow<AppResponse<Unit>> =
        firebaseRepository.delete(collection, id)

    override fun entityExists(id: String): Flow<AppResponse<Unit>> =
        firebaseRepository.entityExists(collection, id)

    override fun fetchByDocument(documentParams: DocumentParameters) =
        firebaseRepository.documentFetch(createFirestoreRequest(documentParams))

    override fun fetchByQuery(queryParams: QueryParameters) =
        firebaseRepository.queryFetch(createFirestoreRequest(queryParams))

    override fun createFirestoreRequest(params: SearchParameters) =
        FirebaseRequest.create(BusinessCentralDto::class.java)
            .setCollection(collection)
            .setParams(params)
            .build()

}*/
