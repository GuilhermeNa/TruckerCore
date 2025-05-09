package com.example.truckercore.model.modules.employee.autonomous.use_cases

import com.example.truckercore.model.modules.employee.autonomous.data.Autonomous
import com.example.truckercore.model.modules.employee.autonomous.specification.AutonomousSpec
import com.example.truckercore.model.shared.utils.sealeds.AppResponse

interface GetAutonomousUseCase {

    suspend operator fun invoke(spec: AutonomousSpec): AppResponse<Autonomous>

}