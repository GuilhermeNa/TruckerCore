package com.example.truckercore.model.modules.vip.use_cases.interfaces

import com.example.truckercore.model.modules.vip.entity.Vip
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the use case that fetches VIP records.
 *
 * This interface defines a method for retrieving a list of VIP records based on the provided query parameters.
 * It allows filtering of the records using specific query settings.
 */
internal interface GetVipUseCase {

    /**
     * Fetches a list of vip records based on the provided query settings.
     * This method allows filtering the [Vip] records using a list of query settings.
     *
     * @param queryParams The query parameters to filter the user records.
     * @return A [Flow] of:
     * - [Response.Success] containing a list of [Vip] objects that match the query.
     * - [Response.Empty] if no user records match the query criteria.
     */
    fun execute(queryParams: QueryParameters): Flow<Response<List<Vip>>>

}