package com.example.truckercore.modules.fleet.truck.service

import com.example.truckercore.shared.abstractions.Service
import com.example.truckercore.infrastructure.util.ExceptionHandler
import com.example.truckercore.modules.fleet.truck.aggregation.TruckWithDetails
import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.AggregateTruckWithDetailsUseCase
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.GetTruckUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class TruckServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val getTruck: GetTruckUseCase,
    private val getTruckWithDetailsUseCase: AggregateTruckWithDetailsUseCase
) : Service(exceptionHandler), TruckService {

    override fun fetchTruck(documentParam: DocumentParameters): Flow<Response<Truck>> =
        runSafe { getTruck.execute(documentParam) }

    override fun fetchTruck(queryParam: QueryParameters): Flow<Response<List<Truck>>> =
        runSafe { getTruck.execute(queryParam) }

    override fun fetchTruckWithDetails(
        documentParam: DocumentParameters
    ): Flow<Response<TruckWithDetails>> =
        runSafe { getTruckWithDetailsUseCase.execute(documentParam) }

    override fun fetchTruckWithDetails(
        queryParam: QueryParameters
    ): Flow<Response<List<TruckWithDetails>>> =
        runSafe { getTruckWithDetailsUseCase.execute(queryParam) }

}