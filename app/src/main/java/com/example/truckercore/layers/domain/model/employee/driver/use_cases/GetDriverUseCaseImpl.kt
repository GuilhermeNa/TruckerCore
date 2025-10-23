package com.example.truckercore.layers.domain.model.employee.driver.use_cases

import com.example.truckercore.data.infrastructure.repository.data.contracts.DataRepository
import com.example.truckercore.data.modules.employee.driver.data.Driver
import com.example.truckercore.data.modules.employee.driver.data.DriverDto
import com.example.truckercore.data.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.data.modules.employee.driver.specification.DriverSpec
import com.example.truckercore.data.shared.outcome.data.DataOutcome
import com.example.truckercore.core.expressions.getOrElse
import com.example.truckercore.core.expressions.handleErrorResponse

class GetDriverUseCaseImpl(
    private val dataRepository: DataRepository
) : com.example.truckercore.layers.domain.model.employee.driver.use_cases.GetDriverUseCase {

    override suspend fun invoke(spec: DriverSpec): DataOutcome<Driver> =
        try {
            dataRepository.findOneBy(spec)
                .getOrElse { unsuccessful -> return unsuccessful }
                .let { getSuccessResponse(it) }

        } catch (e: Exception) {
            e.handleErrorResponse("${com.example.truckercore.layers.domain.model.employee.driver.use_cases.GetDriverUseCaseImpl.Companion.UNKNOWN_ERR_MSG} $spec")
        }

    private fun getSuccessResponse(dto: DriverDto): DataOutcome.Success<Driver> {
        val driver = DriverMapper.toEntity(dto)
        return DataOutcome.Success(driver)
    }

    companion object {
        private const val UNKNOWN_ERR_MSG = "Unknown error occurred while fetching a Driver."
    }

}