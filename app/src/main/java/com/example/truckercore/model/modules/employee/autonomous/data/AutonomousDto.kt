package com.example.truckercore.model.modules.employee.autonomous.data

import com.example.truckercore.model.modules.employee._shared.contracts.EmployeeDto
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.model.modules._shared.enums.PersistenceState

data class AutonomousDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistenceState: PersistenceState? = null,
    override val name: String? = null,
    override val email: String? = null,
    val userId: String? = null,
    val state: EligibleState? = null
) : EmployeeDto
