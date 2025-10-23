package com.example.truckercore.layers.domain.model.employee.autonomous.mapper

import com.example.truckercore.core.classes.Email
import com.example.truckercore.core.classes.FullName
import com.example.truckercore.data.modules._shared._contracts.mapper.Mapper
import com.example.truckercore.data.modules.company.data.CompanyID
import com.example.truckercore.data.modules.employee.autonomous.data.AutID
import com.example.truckercore.data.modules.employee.autonomous.data.Autonomous
import com.example.truckercore.data.modules.employee.autonomous.data.AutonomousDto
import com.example.truckercore.data.modules.user.data.UserID

object AutonomousMapper : Mapper<Autonomous, AutonomousDto> {

    override fun toDto(entity: Autonomous): AutonomousDto =
        try {
            AutonomousDto(
                id = entity.id.value,
                companyId = entity.companyId.value,
                persistenceState = entity.persistenceState,
                name = entity.name.value,
                email = entity.email?.value,
                userId = entity.userId?.value,
                state = entity.eligibleState
            )
        } catch (e: Exception) {
            handleError(entity, e)
        }

    override fun toEntity(dto: AutonomousDto): Autonomous =
        try {
            Autonomous(
                id = AutID(dto.id!!),
                name = FullName.from(dto.name!!),
                companyId = CompanyID(dto.companyId!!),
                email = dto.email?.let { Email.from(it) },
                userId = dto.userId?.let { UserID(it) },
                persistenceState = dto.persistenceState!!,
                eligibleState = dto.state!!
            )
        } catch (e: Exception) {
            handleError(dto, e)
        }

}