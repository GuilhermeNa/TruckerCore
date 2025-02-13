package com.example.truckercore.modules.fleet.truck.use_cases.interfaces

import com.example.truckercore.modules.fleet.truck.aggregation.TruckWithDetails
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal interface AggregateTruckWithDetailsUseCase {

    fun execute(documentParams: DocumentParameters): Flow<Response<TruckWithDetails>>

    fun execute(queryParams: QueryParameters): Flow<Response<List<TruckWithDetails>>>

}