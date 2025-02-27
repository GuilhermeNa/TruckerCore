package com.example.truckercore.modules.fleet.truck.use_cases.interfaces

import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Get Truck Use Case, responsible for retrieving truck data.
 * This use case fetches truck records either by document parameters or by query parameters.
 * The data is then validated and mapped to the appropriate [Truck] entity.
 *
 * @see Truck
 * @see Response
 * @see TruckDto
 */
internal interface GetTruckUseCase {

    /**
     * Fetches a single [Truck] based on document parameters.
     *
     * @param documentParams The document parameters to filter the truck record.
     * @return A [Flow] containing:
     * - [Response.Success] with the found [Truck] entity.
     * - [Response.Empty] if no truck was found matching the provided parameters.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<Truck>>

    /**
     * Fetches a list of [Truck] records based on query parameters.
     *
     * @param queryParams The query parameters to filter the truck records.
     * @return A [Flow] containing:
     * - [Response.Success] with a list of found [Truck] entities.
     * - [Response.Empty] if no trucks match the query parameters.
     */
    fun execute(queryParams: QueryParameters): Flow<Response<List<Truck>>>

}