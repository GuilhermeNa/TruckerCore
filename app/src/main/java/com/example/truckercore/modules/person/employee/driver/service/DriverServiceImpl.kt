package com.example.truckercore.modules.person.employee.driver.service

import com.example.truckercore.infrastructure.util.ExceptionHandler
import com.example.truckercore.modules.person.employee.driver.entity.Driver
import com.example.truckercore.modules.person.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.modules.person.shared.person_details.GetPersonWithDetailsUseCase
import com.example.truckercore.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.shared.abstractions.Service
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class DriverServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val getDriver: GetDriverUseCase,
    private val getPersonWD: GetPersonWithDetailsUseCase
) : Service(exceptionHandler), DriverService {

    override fun fetchDriver(
        documentParam: DocumentParameters
    ): Flow<Response<Driver>> =
        runSafe { getDriver.execute(documentParam) }

    override fun fetchDriverWithDetails(
        documentParam: DocumentParameters
    ): Flow<Response<PersonWithDetails>> =
        runSafe { getPersonWD.execute(documentParam, PersonCategory.DRIVER) }

}