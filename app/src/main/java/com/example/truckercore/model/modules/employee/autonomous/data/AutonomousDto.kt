package com.example.truckercore.model.modules.employee.autonomous.data

import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.modules.employee._contracts.EmployeeDto
import com.example.truckercore.model.shared.enums.Persistence

data class AutonomousDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistence: Persistence? = null,
    override val name: String? = null,
    override val email: String? = null,
) : EmployeeDto {

    override fun copyWith(id: String?): BaseDto {
        TODO("Not yet implemented")
    }

}
