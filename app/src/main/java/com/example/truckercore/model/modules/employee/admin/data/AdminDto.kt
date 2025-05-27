package com.example.truckercore.model.modules.employee.admin.data

import com.example.truckercore.model.modules.employee._shared.contracts.EmployeeDto
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.model.modules._shared.enums.PersistenceState

data class AdminDto(
    override val id: String? = null,
    override val name: String? = null,
    override val companyId: String? = null,
    override val email: String? = null,
    override val persistenceState: PersistenceState? = null,
    val userId: String? = null,
    val state: EligibleState? = null
) : EmployeeDto {

}