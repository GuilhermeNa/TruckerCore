package com.example.truckercore.model.modules.employee.driver.data

import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.Dto
import com.example.truckercore.model.modules.employee._contracts.EmployeeDto
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleState

data class DriverDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistence: Persistence? = null,
    override val name: String? = null,
    override val email: String? = null,
    val userId: String? = null,
    val state: EligibleState? = null
): EmployeeDto