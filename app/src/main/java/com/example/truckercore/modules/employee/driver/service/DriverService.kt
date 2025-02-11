package com.example.truckercore.modules.employee.driver.service

import com.example.truckercore.modules.employee.driver.aggregations.DriverWithDetails
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

interface DriverService {

    fun fetchDriver(documentParam: DocumentParameters): Flow<Response<Driver>>

    fun fetchDriverWithDetails(documentParam: DocumentParameters): Flow<Response<DriverWithDetails>>

}