package com.example.truckercore.modules.fleet.shared.module.licensing.service

import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Licensing Service, responsible for communicating with the backend
 * to fetch and manage licensing data. This service acts as an intermediary layer that
 * allows applications to interact with the backend for interacting with licensing records.
 *
 * @see Licensing
 * @see LicensingWithFile
 * @see Response
 */
interface LicensingService {

    /**
     * Fetches a single licensing record based on document parameters.
     *
     * @param documentParam The document parameters to filter the licensing records.
     * @return A [Flow] containing a [Response] with the [Licensing] record.
     */
    suspend fun fetchLicensing(documentParam: DocumentParameters): Flow<Response<Licensing>>

    /**
     * Fetches a list of licensing records based on query parameters.
     *
     * @param queryParam The query parameters to filter the licensing records.
     * @return A [Flow] containing a [Response] with a list of [Licensing] records.
     */
    suspend fun fetchLicensing(queryParam: QueryParameters): Flow<Response<List<Licensing>>>

    /**
     * Fetches a single licensing record along with its associated files based on document parameters.
     *
     * @param documentParam The document parameters to filter the licensing records with files.
     * @return A [Flow] containing a [Response] with the [LicensingWithFile] record.
     */
    suspend fun fetchLicensingWithFiles(documentParam: DocumentParameters): Flow<Response<LicensingWithFile>>

    /**
     * Fetches a list of licensing records along with their associated files based on query parameters.
     *
     * @param queryParam The query parameters to filter the licensing records with files.
     * @return A [Flow] containing a [Response] with a list of [LicensingWithFile] records.
     */
    suspend fun fetchLicensingWithFiles(queryParam: QueryParameters): Flow<Response<List<LicensingWithFile>>>

}