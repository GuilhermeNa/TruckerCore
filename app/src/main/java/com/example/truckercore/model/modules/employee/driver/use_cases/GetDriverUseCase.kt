package com.example.truckercore.model.modules.employee.driver.use_cases

import com.example.truckercore.model.modules.employee.driver.data.Driver
import com.example.truckercore.model.modules.employee.driver.specification.DriverSpec
import com.example.truckercore._utils.classes.AppResponse

interface GetDriverUseCase {

    suspend operator fun invoke(spec: DriverSpec): AppResponse<Driver>

}