package com.example.truckercore.model.shared.modules.personal_data.repository

import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.database.firebase.util.FirebaseRequest
import com.example.truckercore.model.shared.abstractions.Repository
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.parameters.SearchParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class PersonalDataRepositoryImpl(
    private val firebaseRepository: FirebaseRepository,
    private val collection: Collection
) : Repository(), PersonalDataRepository {

    override fun <T : Dto> create(dto: T): Flow<Response<String>> =
        firebaseRepository.create(collection, dto)

    override fun <T : Dto> update(dto: T): Flow<Response<Unit>> =
        firebaseRepository.update(collection, dto)

    override fun delete(id: String): Flow<Response<Unit>> =
        firebaseRepository.delete(collection, id)

    override fun entityExists(id: String) =
        firebaseRepository.entityExists(collection, id)

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<PersonalDataDto>> =
        firebaseRepository.documentFetch(createFirestoreRequest(documentParams))

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<PersonalDataDto>>> =
        firebaseRepository.queryFetch(createFirestoreRequest(queryParams))

    override fun createFirestoreRequest(params: SearchParameters) =
        FirebaseRequest.create(PersonalDataDto::class.java)
            .setCollection(collection)
            .setParams(params)
            .build()

}