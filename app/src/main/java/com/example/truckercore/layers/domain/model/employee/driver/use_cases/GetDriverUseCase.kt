package com.example.truckercore.layers.domain.model.employee.driver.use_cases

import com.example.truckercore.data.modules.employee.driver.data.Driver
import com.example.truckercore.data.modules.employee.driver.specification.DriverSpec
import com.example.truckercore.data.shared.outcome.data.DataOutcome

interface GetDriverUseCase {

    suspend operator fun invoke(spec: DriverSpec): DataOutcome<Driver>

}