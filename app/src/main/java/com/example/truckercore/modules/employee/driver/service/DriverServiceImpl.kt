package com.example.truckercore.modules.employee.driver.service

import com.example.truckercore.infrastructure.api.Service
import com.example.truckercore.infrastructure.exceptions.ExceptionHandler
import com.example.truckercore.modules.employee.admin.aggregations.AdminWithDetails
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.admin.service.AdminService
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.AggregateAdminWithDetails
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.modules.employee.driver.aggregations.DriverWithDetails
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.AggregateDriverWithDetails
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class DriverServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val getDriver: GetDriverUseCase,
    private val getDriverWithDetails: AggregateDriverWithDetails
) : Service(exceptionHandler), DriverService {

    override fun fetchDriver(
        documentParam: DocumentParameters
    ): Flow<Response<Driver>> =
        runSafe { getDriver.execute(documentParam) }

    override fun fetchDriverWithDetails(
        documentParam: DocumentParameters
    ): Flow<Response<DriverWithDetails>> =
        runSafe { getDriverWithDetails.execute(documentParam) }

}