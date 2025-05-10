package com.example.truckercore.model.modules.employee.autonomous.factory

import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.modules.employee._shared.EmployeeForm
import com.example.truckercore.model.modules.employee.autonomous.data.AutID
import com.example.truckercore.model.modules.employee.autonomous.data.Autonomous
import com.example.truckercore.model.modules.employee.autonomous.data.AutonomousDto
import com.example.truckercore.model.shared.enums.Persistence

object AutonomousFactory {

    operator fun invoke(form: EmployeeForm): Autonomous {
        return Autonomous(
            id = AutID(ID.generate()),
            companyId = form.companyId,
            name = form.name,
            email = form.email
        )
    }

}