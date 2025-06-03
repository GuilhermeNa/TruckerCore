package com.example.truckercore.model.modules.employee.autonomous.use_cases

import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.employee.autonomous.data.Autonomous
import com.example.truckercore.model.modules.employee.autonomous.data.AutonomousDto
import com.example.truckercore.model.modules.employee.autonomous.mapper.AutonomousMapper
import com.example.truckercore.model.modules.employee.autonomous.specification.AutonomousSpec
import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore._shared.expressions.getOrElse
import com.example.truckercore._shared.expressions.handleErrorResponse

class GetAutonomousUseCaseImpl(
    private val dataRepository: DataRepository
) : GetAutonomousUseCase {

    override suspend fun invoke(spec: AutonomousSpec): AppResponse<Autonomous> =
        try {
            dataRepository.findOneBy(spec)
                .getOrElse { unsuccessful -> return unsuccessful }
                .let { getSuccessResponse(it) }

        } catch (e: Exception) {
            e.handleErrorResponse("$UNKNOWN_ERR_MSG $spec")
        }

    private fun getSuccessResponse(dto: AutonomousDto): AppResponse.Success<Autonomous> {
        val autonomous = AutonomousMapper.toEntity(dto)
        return AppResponse.Success(autonomous)
    }

    companion object {
        private const val UNKNOWN_ERR_MSG = "Unknown error occurred while fetching an Autonomous."
    }

}