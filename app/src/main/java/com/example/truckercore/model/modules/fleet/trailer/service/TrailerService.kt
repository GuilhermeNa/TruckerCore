package com.example.truckercore.model.modules.fleet.trailer.service

import com.example.truckercore.model.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Trailer Service, responsible for interacting with backend
 * services to fetch and manage trailer data. This service acts as an intermediary
 * layer for applications to access trailer records, either simple or with associated details.
 *
 * @see Trailer
 * @see TrailerWithDetails
 * @see AppResponse
 */
interface TrailerService {

    /**
     * Fetches a single [Trailer] based on document parameters.
     *
     * @param documentParam The document parameters to filter the trailer record.
     * @return A [Flow] containing:
     * - [AppResponse.Success] with the found [Trailer] record.
     * - [AppResponse.Empty] if no trailer was found matching the provided parameters.
     * - [AppResponse.Error] if an error occurs during the operation.
     */
    fun fetchTrailer(documentParam: DocumentParameters): Flow<AppResponse<Trailer>>

    /**
     * Fetches a list of [Trailer] records based on query parameters.
     *
     * @param queryParam The query parameters to filter the trailer records.
     * @return A [Flow] containing:
     * - [AppResponse.Success] with a list of found [Trailer] records.
     * - [AppResponse.Empty] if no trailers match the query parameters.
     * - [AppResponse.Error] if an error occurs during the operation.
     */
    fun fetchTrailerList(queryParam: QueryParameters): Flow<AppResponse<List<Trailer>>>

    /**
     * Fetches a single [TrailerWithDetails] record based on document parameters.
     *
     * @param documentParam The document parameters to filter the trailer with associated details.
     * @return A [Flow] containing:
     * - [AppResponse.Success] with the found [TrailerWithDetails] record.
     * - [AppResponse.Empty] if no trailer with details was found matching the provided parameters.
     * - [AppResponse.Error] if an error occurs during the operation.
     */
    fun fetchTrailerWithDetails(documentParam: DocumentParameters): Flow<AppResponse<TrailerWithDetails>>

    /**
     * Fetches a list of [TrailerWithDetails] records based on query parameters.
     *
     * @param queryParam The query parameters to filter the trailer with associated details.
     * @return A [Flow] containing:
     * - [AppResponse.Success] with a list of found [TrailerWithDetails] records.
     * - [Response.Empty] if no trailers with details match the query parameters.
     * - [Response.Error] if an error occurs during the operation.
     */
    fun fetchTrailerWithDetailsList(queryParam: QueryParameters): Flow<Response<List<TrailerWithDetails>>>

}