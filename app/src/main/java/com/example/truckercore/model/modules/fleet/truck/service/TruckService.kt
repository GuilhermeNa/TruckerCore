package com.example.truckercore.model.modules.fleet.truck.service

import com.example.truckercore.model.modules.fleet.truck.aggregation.TruckWithDetails
import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Truck Service, responsible for interacting with backend
 * services to fetch and manage truck data. This service acts as an intermediary
 * layer for applications to access truck records, either simple or with associated details.
 *
 * @see Truck
 * @see TruckWithDetails
 * @see Response
 */
interface TruckService {

    /**
     * Fetches a single [Truck] based on document parameters.
     *
     * @param documentParam The document parameters to filter the truck record.
     * @return A [Flow] containing:
     * - [Response.Success] with the found [Truck] record.
     * - [Response.Empty] if no truck was found matching the provided parameters.
     * - [Response.Error] if an error occurs during the operation.
     */
    fun fetchTruck(documentParam: DocumentParameters): Flow<Response<Truck>>

    /**
     * Fetches a list of [Truck] records based on query parameters.
     *
     * @param queryParam The query parameters to filter the truck records.
     * @return A [Flow] containing:
     * - [Response.Success] with a list of found [Truck] records.
     * - [Response.Empty] if no trucks match the query parameters.
     * - [Response.Error] if an error occurs during the operation.
     */
    fun fetchTruck(queryParam: QueryParameters): Flow<Response<List<Truck>>>

    /**
     * Fetches a single [TruckWithDetails] record based on document parameters.
     *
     * @param documentParam The document parameters to filter the truck with associated details.
     * @return A [Flow] containing:
     * - [Response.Success] with the found [TruckWithDetails] record.
     * - [Response.Empty] if no truck with details was found matching the provided parameters.
     * - [Response.Error] if an error occurs during the operation.
     */
    fun fetchTruckWithDetails(documentParam: DocumentParameters): Flow<Response<TruckWithDetails>>

    /**
     * Fetches a list of [TruckWithDetails] records based on query parameters.
     *
     * @param queryParam The query parameters to filter the truck with associated details.
     * @return A [Flow] containing:
     * - [Response.Success] with a list of found [TruckWithDetails] records.
     * - [Response.Empty] if no trucks with details match the query parameters.
     * - [Response.Error] if an error occurs during the operation.
     */
    fun fetchTruckWithDetails(queryParam: QueryParameters): Flow<Response<List<TruckWithDetails>>>

}