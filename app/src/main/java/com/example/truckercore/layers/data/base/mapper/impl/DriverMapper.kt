package com.example.truckercore.layers.data.base.mapper.impl

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.data.base.dto.impl.AdminDto
import com.example.truckercore.layers.data.base.dto.impl.DriverDto
import com.example.truckercore.layers.data.base.mapper.base.Mapper
import com.example.truckercore.layers.domain.base.ids.AdminID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.DriverID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.admin.Admin
import com.example.truckercore.layers.domain.model.driver.Driver

object DriverMapper : Mapper<DriverDto, Driver> {

    override fun toDto(entity: Driver): DriverDto {
        TODO("Not yet implemented")
    }

    override fun toEntity(dto: DriverDto): Driver = try {
        with(dto) {
            Driver(
                id = DriverID(id!!),
                companyId = CompanyID(companyId!!),
                status = status!!,
                name = Name.from(name!!),
                email = Email.from(email!!),
                userId = userId?.let { UserID(it) }
            )
        }
    } catch (e: Exception) {
        throw DataException.Mapping("Error while mapping an DriverDto to entity: $dto")
    }

    override fun toDtos(entities: List<Driver>): List<DriverDto> {
        TODO("Not yet implemented")
    }

    override fun toEntities(dtos: List<DriverDto>): List<Driver> {
        TODO("Not yet implemented")
    }


}