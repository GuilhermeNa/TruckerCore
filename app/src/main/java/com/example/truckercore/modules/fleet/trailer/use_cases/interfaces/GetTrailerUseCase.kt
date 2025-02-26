package com.example.truckercore.modules.fleet.trailer.use_cases.interfaces

import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [Trailer] entity or a list of [Trailer] entities.
 * The implementation of this use case should provide the logic for fetching trailer data, either by
 * individual document parameters or query parameters.
 *
 * @see Trailer
 * @see Response
 */
internal interface GetTrailerUseCase {

    /**
     * Executes the use case to retrieve a single [Trailer] entity based on the provided document parameters.
     *
     * @param documentParams The parameters used to filter and fetch a specific trailer.
     * @return A [Flow] containing:
     * - [Response.Success] with the retrieved [Trailer] entity.
     * - [Response.Empty] if no trailer matching the document parameters is found.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<Trailer>>

    /**
     * Executes the use case to retrieve a list of [Trailer] entities based on the provided query parameters.
     *
     * @param queryParams The query parameters used to filter and fetch multiple trailers.
     * @return A [Flow] containing:
     * - [Response.Success] with a list of retrieved [Trailer] entities.
     * - [Response.Empty] if no trailers matching the query parameters are found.
     */
    fun execute(queryParams: QueryParameters): Flow<Response<List<Trailer>>>

}