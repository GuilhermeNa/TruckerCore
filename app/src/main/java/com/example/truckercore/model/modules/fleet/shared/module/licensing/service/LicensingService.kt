package com.example.truckercore.model.modules.fleet.shared.module.licensing.service

import com.example.truckercore.model.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.model.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
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
     * @return A [Flow] containing:
     * - [Response.Success] with the [Licensing] record if found.
     * - [Response.Empty] if no licensing record is found.
     * - [Response.Error] if an error occurs during the operation.
     */
    fun fetchLicensing(documentParam: DocumentParameters): Flow<Response<Licensing>>

    /**
     * Fetches a list of licensing records based on query parameters.
     *
     * @param queryParam The query parameters to filter the licensing records.
     * @return A [Flow] containing:
     * - [Response.Success] with a list of [Licensing] records if found.
     * - [Response.Empty] if no licensing records are found.
     * - [Response.Error] if an error occurs during the operation.
     */
    fun fetchLicensing(queryParam: QueryParameters): Flow<Response<List<Licensing>>>

    /**
     * Fetches a single licensing record along with its associated files based on document parameters.
     *
     * @param documentParam The document parameters to filter the licensing records with files.
     * @return A [Flow] containing:
     * - [Response.Success] with the [LicensingWithFile] record if found.
     * - [Response.Empty] if no licensing record with files is found.
     * - [Response.Error] if an error occurs during the operation.
     */
    fun fetchLicensingWithFiles(documentParam: DocumentParameters): Flow<Response<LicensingWithFile>>

    /**
     * Fetches a list of licensing records along with their associated files based on query parameters.
     *
     * @param queryParam The query parameters to filter the licensing records with files.
     * @return A [Flow] containing:
     * - [Response.Success] with a list of [LicensingWithFile] records if found.
     * - [Response.Empty] if no licensing records with files are found.
     * - [Response.Error] if an error occurs during the operation.
     */
    fun fetchLicensingWithFiles(queryParam: QueryParameters): Flow<Response<List<LicensingWithFile>>>

}