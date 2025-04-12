package com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces

import com.example.truckercore.model.modules.fleet.truck.aggregation.TruckWithDetails
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Aggregate Truck With Details Use Case, responsible for aggregating
 * data related to trucks, trailers, and licensing information. This use case combines
 * data from multiple sources into a single `TruckWithDetails` object, providing detailed
 * truck records, including associated trailers and licensing information.
 *
 * @see TruckWithDetails
 * @see AppResponse
 * @see Truck
 * @see TrailerWithDetails
 * @see LicensingWithFile
 */
internal interface AggregateTruckWithDetailsUseCase {

    /**
     * Aggregates a single [TruckWithDetails] based on document parameters.
     *
     * @param documentParams The document parameters to filter the truck, trailer, and licensing data.
     * @return A [Flow] containing:
     * - [AppResponse.Success] with the aggregated [TruckWithDetails] record.
     * - [AppResponse.Empty] if no truck data is found matching the provided parameters.
     */
    fun execute(documentParams: DocumentParameters): Flow<AppResponse<TruckWithDetails>>

    /**
     * Aggregates a list of [TruckWithDetails] based on query parameters.
     *
     * @param queryParams The query parameters to filter the truck, trailer, and licensing data.
     * @return A [Flow] containing:
     * - [AppResponse.Success] with a list of aggregated [TruckWithDetails] records.
     * - [AppResponse.Empty] if no truck data matches the query parameters.
     */
    fun execute(queryParams: QueryParameters): Flow<AppResponse<List<TruckWithDetails>>>

}