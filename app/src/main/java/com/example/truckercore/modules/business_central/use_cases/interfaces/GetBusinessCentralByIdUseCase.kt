package com.example.truckercore.modules.business_central.use_cases.interfaces

import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [BusinessCentral] entity by its ID.
 *
 * This interface defines the contract for a use case that retrieves a [BusinessCentral] entity based on the provided ID.
 * The retrieval is asynchronous and returns a [Flow] of [Response] which allows the caller to collect the result reactively.
 * It may include operations like validation, permission checks, and data fetching from a repository or database.
 */
internal interface GetBusinessCentralByIdUseCase {

    /**
     * Fetches a single [BusinessCentral] entity based on the provided document parameters.
     * This method retrieves the details of the business central as a [BusinessCentral] object.
     *
     * @param documentParams The document parameters to filter the business central records.
     * @return A [Flow] of:
     * - [Response.Success] containing the [BusinessCentral] object if the entity was found.
     * - [Response.Empty] if no business central entity exists with the given document parameters.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<BusinessCentral>>

    /**
     * Fetches a list of [BusinessCentral] based on the provided query parameters.
     *
     * @param queryParams The query parameters to filter the business central.
     * @return A [Flow] of:
     * - [Response.Success] containing a list of [BusinessCentral] objects that match the query.
     * - [Response.Empty] if no records are found for the given business central.
     */
    fun execute(queryParams: QueryParameters): Flow<Response<List<BusinessCentral>>>

}