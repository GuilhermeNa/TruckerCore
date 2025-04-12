package com.example.truckercore.model.modules.fleet.truck.service

import com.example.truckercore.model.modules.fleet.truck.aggregation.TruckWithDetails
import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Truck Service, responsible for interacting with backend
 * services to fetch and manage truck data. This service acts as an intermediary
 * layer for applications to access truck records, either simple or with associated details.
 *
 * @see Truck
 * @see TruckWithDetails
 * @see AppResponse
 */
interface TruckService {

    /**
     * Fetches a single [Truck] based on document parameters.
     *
     * @param documentParam The document parameters to filter the truck record.
     * @return A [Flow] containing:
     * - [AppResponse.Success] with the found [Truck] record.
     * - [AppResponse.Empty] if no truck was found matching the provided parameters.
     * - [AppResponse.Error] if an error occurs during the operation.
     */
    fun fetchTruck(documentParam: DocumentParameters): Flow<AppResponse<Truck>>

    /**
     * Fetches a list of [Truck] records based on query parameters.
     *
     * @param queryParam The query parameters to filter the truck records.
     * @return A [Flow] containing:
     * - [AppResponse.Success] with a list of found [Truck] records.
     * - [AppResponse.Empty] if no trucks match the query parameters.
     * - [AppResponse.Error] if an error occurs during the operation.
     */
    fun fetchTruck(queryParam: QueryParameters): Flow<AppResponse<List<Truck>>>

    /**
     * Fetches a single [TruckWithDetails] record based on document parameters.
     *
     * @param documentParam The document parameters to filter the truck with associated details.
     * @return A [Flow] containing:
     * - [AppResponse.Success] with the found [TruckWithDetails] record.
     * - [AppResponse.Empty] if no truck with details was found matching the provided parameters.
     * - [AppResponse.Error] if an error occurs during the operation.
     */
    fun fetchTruckWithDetails(documentParam: DocumentParameters): Flow<AppResponse<TruckWithDetails>>

    /**
     * Fetches a list of [TruckWithDetails] records based on query parameters.
     *
     * @param queryParam The query parameters to filter the truck with associated details.
     * @return A [Flow] containing:
     * - [AppResponse.Success] with a list of found [TruckWithDetails] records.
     * - [AppResponse.Empty] if no trucks with details match the query parameters.
     * - [AppResponse.Error] if an error occurs during the operation.
     */
    fun fetchTruckWithDetails(queryParam: QueryParameters): Flow<AppResponse<List<TruckWithDetails>>>

}