package com.example.truckercore.model.modules.employee.admin.mapper

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.modules._shared.contracts.mapper.Mapper
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.employee.admin.data.Admin
import com.example.truckercore.model.modules.employee.admin.data.AdminDto
import com.example.truckercore.model.modules.employee.admin.data.AdminID
import com.example.truckercore.model.modules.user.data.UserID

object AdminMapper : Mapper<Admin, AdminDto> {

    override fun toDto(entity: Admin): AdminDto =
        try {
            AdminDto(
                id = entity.idValue,
                companyId = entity.companyIdValue,
                persistence = entity.persistenceState,
                name = entity.nameValue,
                email = entity.emailValue,
                userId = entity.userIdValue,
                state = entity.eligibleState
            )
        } catch (e: Exception) {
            handleError(entity, e)
        }

    override fun toEntity(dto: AdminDto): Admin =
        try {
            Admin(
                id = AdminID(dto.id!!),
                name = FullName.from(dto.name!!),
                companyId = CompanyID(dto.companyId!!),
                email = dto.email?.let { Email.from(it) },
                userId = dto.userId?.let { UserID(it) },
                persistenceState = dto.persistence!!,
                eligibleState = dto.state!!
            )
        } catch (e: Exception) {
            handleError(dto, e)
        }

}