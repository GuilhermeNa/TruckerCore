package com.example.truckercore.model.modules.employee.driver.use_cases

import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.employee.driver.data.Driver
import com.example.truckercore.model.modules.employee.driver.data.DriverDto
import com.example.truckercore.model.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.model.modules.employee.driver.specification.DriverSpec
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.mapAppResponse

class GetDriverUseCaseImpl(
    private val dataRepository: DataRepository
) : GetDriverUseCase {

    override suspend fun invoke(spec: DriverSpec): AppResponse<Driver> {
        return dataRepository.findOneBy(spec).mapAppResponse(
            onSuccess = { getSuccessResponse(it) },
            onEmpty = { AppResponse.Empty },
            onError = { AppResponse.Error(it) }
        )
    }

    private fun getSuccessResponse(dto: DriverDto): AppResponse.Success<Driver> {
        val driver = DriverMapper.toEntity(dto)
        return AppResponse.Success(driver)
    }

}