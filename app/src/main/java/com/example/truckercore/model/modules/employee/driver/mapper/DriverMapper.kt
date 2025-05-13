package com.example.truckercore.model.modules.employee.driver.mapper

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.modules._contracts.mapper.Mapper
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.employee.driver.data.Driver
import com.example.truckercore.model.modules.employee.driver.data.DriverDto
import com.example.truckercore.model.modules.employee.driver.data.DriverID
import com.example.truckercore.model.modules.user.data.UserID

object DriverMapper : Mapper<Driver, DriverDto> {

    override fun toDto(entity: Driver): DriverDto =
        try {
            DriverDto(
                id = entity.idValue,
                companyId = entity.companyIdValue,
                persistence = entity.persistence,
                name = entity.nameValue,
                email = entity.emailValue,
                userId = entity.userIdValue,
                state = entity.state
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
                persistence = dto.persistence!!,
                state = dto.state!!
            )
        } catch (e: Exception) {
            handleError(dto, e)
        }

}