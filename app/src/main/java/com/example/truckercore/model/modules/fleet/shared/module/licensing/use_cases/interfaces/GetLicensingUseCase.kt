package com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.interfaces

import com.example.truckercore.model.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [Licensing].
 *
 * @see Licensing
 * @see Response
 */
internal interface GetLicensingUseCase {

    /**
     * Fetches a single licensing record based on the provided ID.
     * This method retrieves the licensing details as a [Licensing] object.
     *
     * @param documentParams The document parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [Response.Success] containing the [Licensing] object if the licensing record was found.
     * - [Response.Empty] if no licensing record exists with the given ID.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<Licensing>>

    /**
     * Fetches a list of licensing records based on the provided query settings.
     * This method allows filtering the [Licensing] records using a list of query settings.
     *
     * @param queryParams The query parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [Response.Success] containing a list of [Licensing] objects that match the query.
     * - [Response.Empty] if no licensing records match the query criteria.
     */
    fun execute(queryParams: QueryParameters): Flow<Response<List<Licensing>>>

}