package com.example.truckercore.modules.fleet.truck.service

import com.example.truckercore.modules.fleet.truck.configs.TruckFetchConfig
import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.fleet.truck.configs.TruckWithDetails
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

interface TruckService {

    suspend fun getTruck(user: User, truckId: String): Flow<Response<Truck>>

    suspend fun getTruckWithDetails(
        user: User,
        truckId: String,
        details: TruckFetchConfig
    ): Flow<Response<TruckWithDetails>>

}