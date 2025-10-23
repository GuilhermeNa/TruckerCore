package com.example.truckercore.layers.domain.model.employee.autonomous.use_cases

import com.example.truckercore.data.modules.employee.autonomous.data.Autonomous
import com.example.truckercore.data.modules.employee.autonomous.specification.AutonomousSpec
import com.example.truckercore.data.shared.outcome.data.DataOutcome

interface GetAutonomousUseCase {

    suspend operator fun invoke(spec: AutonomousSpec): DataOutcome<Autonomous>

}