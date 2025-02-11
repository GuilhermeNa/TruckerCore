package com.example.truckercore.modules.fleet.trailer.use_cases.interfaces

import com.example.truckercore.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface AggregateTrailerWithDetailsUseCase {

    fun execute(documentParams: DocumentParameters): Flow<Response<TrailerWithDetails>>

    fun execute(queryParams: QueryParameters): Flow<Response<List<TrailerWithDetails>>>

}