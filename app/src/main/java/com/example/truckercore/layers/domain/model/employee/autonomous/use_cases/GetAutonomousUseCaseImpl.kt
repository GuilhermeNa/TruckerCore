package com.example.truckercore.layers.domain.model.employee.autonomous.use_cases

import com.example.truckercore.data.infrastructure.repository.data.contracts.DataRepository
import com.example.truckercore.data.modules.employee.autonomous.data.Autonomous
import com.example.truckercore.data.modules.employee.autonomous.data.AutonomousDto
import com.example.truckercore.data.modules.employee.autonomous.mapper.AutonomousMapper
import com.example.truckercore.data.modules.employee.autonomous.specification.AutonomousSpec
import com.example.truckercore.data.shared.outcome.data.DataOutcome
import com.example.truckercore.core.expressions.getOrElse
import com.example.truckercore.core.expressions.handleErrorResponse

class GetAutonomousUseCaseImpl(
    private val dataRepository: DataRepository
) : GetAutonomousUseCase {

    override suspend fun invoke(spec: AutonomousSpec): DataOutcome<Autonomous> =
        try {
            dataRepository.findOneBy(spec)
                .getOrElse { unsuccessful -> return unsuccessful }
                .let { getSuccessResponse(it) }

        } catch (e: Exception) {
            e.handleErrorResponse("$UNKNOWN_ERR_MSG $spec")
        }

    private fun getSuccessResponse(dto: AutonomousDto): DataOutcome.Success<Autonomous> {
        val autonomous = AutonomousMapper.toEntity(dto)
        return DataOutcome.Success(autonomous)
    }

    companion object {
        private const val UNKNOWN_ERR_MSG = "Unknown error occurred while fetching an Autonomous."
    }

}