package com.example.truckercore.layers.domain.model.employee.autonomous.data

import com.example.truckercore.data.modules.employee._shared.contracts.EmployeeDto
import com.example.truckercore.data.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.data.modules._shared.enums.PersistenceState

data class AutonomousDto(
    override val id: String? = null,
    override val companyId: String? = null,
    override val persistenceState: PersistenceState? = null,
    override val name: String? = null,
    override val email: String? = null,
    val userId: String? = null,
    val state: EligibleState? = null
) : EmployeeDto
