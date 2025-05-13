/*
package com.example.truckercore.model.modules._previous_sample.business_central.use_cases.interfaces

import com.example.truckercore.model.modules.business_central.entity.BusinessCentral
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore._utils.classes.AppResponse
import kotlinx.coroutines.flow.Flow

*/
/**
 * Interface representing the use case for retrieving a [BusinessCentral] entity by its ID.
 *
 * This interface defines the contract for a use case that retrieves a [BusinessCentral] entity based on the provided ID.
 * The retrieval is asynchronous and returns a [Flow] of [Response] which allows the caller to collect the result reactively.
 * It may include operations like validation, permission checks, and data fetching from a repository or database.
 *//*

internal interface GetBusinessCentralUseCase {

    */
/**
     * Fetches a single [BusinessCentral] entity based on the provided document parameters.
     *
     * This method retrieves a [BusinessCentral] entity by filtering it based on the provided document parameters.
     * It involves fetching the data from a repository, validating it, and mapping it to a [BusinessCentral] entity.
     *
     * @param documentParams The document parameters used to filter the business central records.
     * @return A [Flow] that emits:
     * - [Response.Success] containing the [BusinessCentral] object if the entity is found.
     * - [Response.Empty] if no entity is found matching the provided document parameters.
     *//*

    fun execute(documentParams: DocumentParameters): Flow<Response<BusinessCentral>>

    */
/**
     * Fetches a list of [BusinessCentral] entities based on the provided query parameters.
     *
     * This method retrieves multiple [BusinessCentral] entities by filtering them based on the provided query parameters.
     * It retrieves a list of matching entities from the repository, validates and maps them to [BusinessCentral] objects.
     * The result is returned as a [Flow] of [Response.Success] or [Response.Empty].
     *
     * @param queryParams The query parameters used to filter the business central records.
     * @return A [Flow] that emits:
     * - [Response.Success] containing a list of [BusinessCentral] objects if any entities match the query.
     * - [Response.Empty] if no entities match the query parameters.
     *//*

    fun execute(queryParams: QueryParameters): Flow<Response<List<BusinessCentral>>>

}*/
