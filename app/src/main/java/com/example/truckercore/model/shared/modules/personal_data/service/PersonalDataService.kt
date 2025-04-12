package com.example.truckercore.model.shared.modules.personal_data.service

import com.example.truckercore.model.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.model.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Personal Data Service, responsible for communicating with the backend
 * to fetch and manage personal data records. This service acts as an intermediary layer that
 * allows applications to interact with the backend for interacting with personal data records.
 *
 * @see PersonalData
 * @see PersonalDataWithDetails
 * @see AppResponse
 */
interface PersonalDataService {

    /**
     * Fetches a single personal data record based on document parameters.
     *
     * @param documentParam The document parameters to filter the personal data records.
     * @return A [Flow] containing a [AppResponse] with the [PersonalData] record.
     */
    fun fetchPersonalData(documentParam: DocumentParameters): Flow<AppResponse<PersonalData>>

    /**
     * Fetches a list of personal data records based on query parameters.
     *
     * @param queryParam The query parameters to filter the personal data records.
     * @return A [Flow] containing a [AppResponse] with a list of [PersonalData] records.
     */
    fun fetchPersonalData(queryParam: QueryParameters): Flow<AppResponse<List<PersonalData>>>

    /**
     * Fetches a single personal data record along with its associated details based on document parameters.
     *
     * @param documentParam The document parameters to filter the personal data records with details.
     * @return A [Flow] containing a [AppResponse] with the [PersonalDataWithFile] record.
     */
    fun fetchPersonalDataWithDetails(documentParam: DocumentParameters): Flow<AppResponse<PersonalDataWithFile>>

    /**
     * Fetches a list of personal data records along with their associated details based on query parameters.
     *
     * @param queryParam The query parameters to filter the personal data records with details.
     * @return A [Flow] containing a [AppResponse] with a list of [PersonalDataWithFile] records.
     */
    fun fetchPersonalDataWithDetails(queryParam: QueryParameters): Flow<AppResponse<List<PersonalDataWithFile>>>

}