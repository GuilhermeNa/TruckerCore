package com.example.truckercore.layers.domain.model.employee.driver.mapper

import com.example.truckercore.core.classes.Email
import com.example.truckercore.core.classes.FullName
import com.example.truckercore.data.modules._shared._contracts.mapper.Mapper
import com.example.truckercore.data.modules.company.data.CompanyID
import com.example.truckercore.data.modules.employee.driver.data.Driver
import com.example.truckercore.data.modules.employee.driver.data.DriverDto
import com.example.truckercore.data.modules.employee.driver.data.DriverID
import com.example.truckercore.data.modules.user.data.UserID

object DriverMapper : Mapper<Driver, DriverDto> {

    override fun toDto(entity: Driver): DriverDto =
        try {
            DriverDto(
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

    override fun toEntity(dto: DriverDto): Driver =
        try {
            Driver(
                id = DriverID(dto.id!!),
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