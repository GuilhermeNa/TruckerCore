package com.example.truckercore.model.modules.employee.admin.mapper

import com.example.truckercore._shared.classes.Email
import com.example.truckercore._shared.classes.FullName
import com.example.truckercore.model.modules._shared._contracts.mapper.Mapper
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.employee.admin.data.Admin
import com.example.truckercore.model.modules.employee.admin.data.AdminDto
import com.example.truckercore.model.modules.employee.admin.data.AdminID
import com.example.truckercore.model.modules.user._contracts.eligible_state.Active
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleState
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleStateDto
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleStateDto.ACTIVE
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleStateDto.SUSPEND
import com.example.truckercore.model.modules.user._contracts.eligible_state.EligibleStateDto.UNREGISTERED
import com.example.truckercore.model.modules.user._contracts.eligible_state.Suspend
import com.example.truckercore.model.modules.user._contracts.eligible_state.Unregistered
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.shared.errors.InvalidStateException

object AdminMapper : Mapper<Admin, AdminDto> {

    override fun toDto(entity: Admin): AdminDto =
        try {
            AdminDto(
                id = entity.idValue,
                companyId = entity.companyIdValue,
                persistenceState = entity.persistenceState,
                name = entity.nameValue,
                email = entity.emailValue,
                userId = entity.userIdValue,
                state = entity.eligibleState.toDto()
            )
        } catch (e: Exception) {
            handleError(entity, e)
        }

    private fun EligibleState.toDto(): EligibleStateDto {
        return when (this) {
            is Active -> ACTIVE
            is Suspend -> SUSPEND
            is Unregistered -> UNREGISTERED
            else -> throw InvalidStateException("Received eligible state is not registered on DTO class.")
        }
    }

    override fun toEntity(dto: AdminDto): Admin =
        try {
            Admin(
                id = AdminID(dto.id!!),
                name = FullName.from(dto.name!!),
                companyId = CompanyID(dto.companyId!!),
                email = dto.email?.let { Email.from(it) },
                userId = dto.userId?.let { UserID(it) },
                persistenceState = dto.persistenceState!!,
                eligibleState = dto.state!!.toEntity()
            )
        } catch (e: Exception) {
            handleError(dto, e)
        }

    private fun EligibleStateDto.toEntity(): EligibleState {
        return when (this) {
            ACTIVE -> Active()
            SUSPEND -> Suspend()
            UNREGISTERED -> Unregistered()
        }
    }

}