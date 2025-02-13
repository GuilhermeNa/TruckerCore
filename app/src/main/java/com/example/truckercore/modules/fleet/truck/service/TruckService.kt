package com.example.truckercore.modules.fleet.truck.service

import com.example.truckercore.modules.fleet.truck.aggregation.TruckWithDetails
import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

interface TruckService {

    fun fetchTruck(documentParam: DocumentParameters): Flow<Response<Truck>>

    fun fetchTruck(queryParam: QueryParameters): Flow<Response<List<Truck>>>

    fun fetchTruckWithDetails(documentParam: DocumentParameters): Flow<Response<TruckWithDetails>>

    fun fetchTruckWithDetails(queryParam: QueryParameters): Flow<Response<List<TruckWithDetails>>>

}