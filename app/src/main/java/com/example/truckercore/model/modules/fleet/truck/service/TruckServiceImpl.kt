package com.example.truckercore.model.modules.fleet.truck.service

import com.example.truckercore.model.infrastructure.util.ExceptionHandler
import com.example.truckercore.model.modules.fleet.truck.aggregation.TruckWithDetails
import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces.AggregateTruckWithDetailsUseCase
import com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces.GetTruckUseCase
import com.example.truckercore.model.shared.abstractions.Service
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

internal class TruckServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val getTruck: GetTruckUseCase,
    private val getTruckWithDetailsUseCase: AggregateTruckWithDetailsUseCase
) : Service(exceptionHandler), TruckService {

    override fun fetchTruck(documentParam: DocumentParameters): Flow<AppResponse<Truck>> =
        runSafe { getTruck.execute(documentParam) }

    override fun fetchTruck(queryParam: QueryParameters): Flow<AppResponse<List<Truck>>> =
        runSafe { getTruck.execute(queryParam) }

    override fun fetchTruckWithDetails(
        documentParam: DocumentParameters
    ): Flow<AppResponse<TruckWithDetails>> =
        runSafe { getTruckWithDetailsUseCase.execute(documentParam) }

    override fun fetchTruckWithDetails(
        queryParam: QueryParameters
    ): Flow<AppResponse<List<TruckWithDetails>>> =
        runSafe { getTruckWithDetailsUseCase.execute(queryParam) }

}