package com.example.truckercore.model.modules.fleet.truck.repository

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.shared.abstractions.Repository
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.SearchParameters

internal class TruckRepositoryImpl(
    private val firebaseRepository: FirebaseRepository,
    private val collection: Collection
) : Repository(), TruckRepository {

    override fun createFirestoreRequest(params: SearchParameters) =
        FirebaseRequest.create(TruckDto::class.java)
            .setCollection(collection)
            .setParams(params)
            .build()

    override fun fetchByDocument(documentParams: DocumentParameters) =
        firebaseRepository.documentFetch(createFirestoreRequest(documentParams))

    override fun fetchByQuery(queryParams: QueryParameters) =
        firebaseRepository.queryFetch(createFirestoreRequest(queryParams))

    override fun <T : Dto> create(dto: T) =
        firebaseRepository.create(collection, dto)

    override fun <T : Dto> update(dto: T) =
        firebaseRepository.update(collection, dto)

    override fun delete(id: String) =
        firebaseRepository.delete(collection, id)

    override fun entityExists(id: String) =
        firebaseRepository.entityExists(collection, id)

}