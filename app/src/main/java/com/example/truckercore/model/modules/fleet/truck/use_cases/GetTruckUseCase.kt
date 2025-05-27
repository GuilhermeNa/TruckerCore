package com.example.truckercore.model.modules.fleet.truck.use_cases

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore.model.modules.fleet.truck.TruckSpec
import com.example.truckercore.model.modules.fleet.truck.data.Truck

interface GetTruckUseCase {

    suspend operator fun invoke(spec: TruckSpec): AppResponse<Truck>

}