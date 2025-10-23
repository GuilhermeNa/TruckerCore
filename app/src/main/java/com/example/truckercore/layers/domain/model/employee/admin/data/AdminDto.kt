package com.example.truckercore.layers.domain.model.employee.admin.data

import com.example.truckercore.layers.domain.base.enums.PersistenceState
import com.example.truckercore.layers.domain.model.employee._shared.contracts.EmployeeDto
import com.example.truckercore.layers.domain.model.user._contracts.eligible_state.EligibleStateDto

data class AdminDto(
    override val id: String? = null,
    override val name: String? = null,
    override val companyId: String? = null,
    override val email: String? = null,
    override val persistenceState: PersistenceState? = null,
    val userId: String? = null,
    val state: EligibleStateDto? = null
) : EmployeeDto {

}