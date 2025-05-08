package com.example.truckercore.model.modules.employee.autonomous.factory

import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.modules.employee._shared.EmployeeForm
import com.example.truckercore.model.modules.employee.autonomous.data.AutonomousDto
import com.example.truckercore.model.shared.enums.Persistence

object AutonomousFactory {

    operator fun invoke(form: EmployeeForm): AutonomousDto {
        return AutonomousDto(
            id = ID.generate(),
            companyId = form.companyId.value,
            persistence = Persistence.ACTIVE,
            name = form.name.value,
            email = form.email.value
        )
    }

}