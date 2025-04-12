package com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces

import com.example.truckercore.model.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines use cases for aggregating trailer data along with associated
 * licensing records and files. This use case allows fetching a trailer with its associated
 * licensing and file records based on different parameters, such as document parameters
 * or query parameters.
 *
 * @see TrailerWithDetails
 * @see AppResponse
 */
internal interface AggregateTrailerWithDetailsUseCase {

    /**
     * Executes the use case to aggregate a single [TrailerWithDetails] record based on
     * document parameters. It fetches the trailer and its associated licensing and file records.
     *
     * @param documentParams The parameters used to filter the trailer and its licensing records.
     * @return A [Flow] containing:
     * - [AppResponse.Success] with the found [TrailerWithDetails] record.
     * - [AppResponse.Empty] if no trailer with details is found.
     */
    fun execute(documentParams: DocumentParameters): Flow<AppResponse<TrailerWithDetails>>

    /**
     * Executes the use case to aggregate a list of [TrailerWithDetails] records based on
     * query parameters. It fetches a list of trailers along with their associated licensing
     * and file records.
     *
     * @param queryParams The query parameters used to filter the trailer and its licensing records.
     * @return A [Flow] containing:
     *     - [Response.Success] with a list of found [TrailerWithDetails] records.
     *     - [Response.Empty] if no trailers with details are found.
     */
    fun execute(queryParams: QueryParameters): Flow<AppResponse<List<TrailerWithDetails>>>

}