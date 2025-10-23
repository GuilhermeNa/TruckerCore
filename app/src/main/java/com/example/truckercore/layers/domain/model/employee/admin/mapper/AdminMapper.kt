package com.example.truckercore.layers.domain.model.employee.admin.mapper

import com.example.truckercore.layers.data.base.mapper.base.Mapper
import com.example.truckercore.layers.domain.model.employee.admin.data.Admin
import com.example.truckercore.layers.domain.model.employee.admin.data.AdminDto

object AdminMapper : Mapper<Admin, AdminDto> {

    override fun toDto(entity: Admin): AdminDto =
        try {
            AdminDto(
                id = entity.idValue,
                companyId = entity.companyIdValue,
                persistenceState = entity.persistence,
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

    private fun EligibleStateDto.toEntity(): EligibleState {
        return when (this) {
            ACTIVE -> Active()
            SUSPEND -> Suspend()
            UNREGISTERED -> Unregistered()
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
                persistence = dto.persistenceState!!,
                eligibleState = dto.state!!.toEntity()
            )
        } catch (e: Exception) {
            handleError(dto, e)
        }


}