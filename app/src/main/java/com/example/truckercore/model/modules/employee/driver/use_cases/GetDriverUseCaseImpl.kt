package com.example.truckercore.model.modules.employee.driver.use_cases

import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.employee.driver.data.Driver
import com.example.truckercore.model.modules.employee.driver.data.DriverDto
import com.example.truckercore.model.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.model.modules.employee.driver.specification.DriverSpec
import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.expressions.getOrElse
import com.example.truckercore._utils.expressions.handleErrorResponse

class GetDriverUseCaseImpl(
    private val dataRepository: DataRepository
) : GetDriverUseCase {

    override suspend fun invoke(spec: DriverSpec): AppResponse<Driver> =
        try {
            dataRepository.findOneBy(spec)
                .getOrElse { unsuccessful -> return unsuccessful }
                .let { getSuccessResponse(it) }

        } catch (e: Exception) {
            e.handleErrorResponse("$UNKNOWN_ERR_MSG $spec")
        }

    private fun getSuccessResponse(dto: DriverDto): AppResponse.Success<Driver> {
        val driver = DriverMapper.toEntity(dto)
        return AppResponse.Success(driver)
    }

    companion object {
        private const val UNKNOWN_ERR_MSG = "Unknown error occurred while fetching a Driver."
    }

}