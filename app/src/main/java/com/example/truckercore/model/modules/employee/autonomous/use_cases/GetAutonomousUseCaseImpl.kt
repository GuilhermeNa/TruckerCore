package com.example.truckercore.model.modules.employee.autonomous.use_cases

import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepository
import com.example.truckercore.model.modules.employee.autonomous.data.Autonomous
import com.example.truckercore.model.modules.employee.autonomous.data.AutonomousDto
import com.example.truckercore.model.modules.employee.autonomous.mapper.AutonomousMapper
import com.example.truckercore.model.modules.employee.autonomous.specification.AutonomousSpec
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.mapAppResponse

class GetAutonomousUseCaseImpl(
    private val dataRepository: DataRepository
) : GetAutonomousUseCase {

    override suspend fun invoke(spec: AutonomousSpec): AppResponse<Autonomous> {
        return dataRepository.findOneBy(spec).mapAppResponse(
            onSuccess = { getSuccessResponse(it) },
            onEmpty = { AppResponse.Empty },
            onError = { AppResponse.Error(it) }
        )
    }

    private fun getSuccessResponse(dto: AutonomousDto): AppResponse.Success<Autonomous> {
        val autonomous = AutonomousMapper.toEntity(dto)
        return AppResponse.Success(autonomous)
    }

}