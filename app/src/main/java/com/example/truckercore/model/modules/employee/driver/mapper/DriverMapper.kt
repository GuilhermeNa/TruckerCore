package com.example.truckercore.model.modules.employee.driver.mapper

import com.example.truckercore.model.modules._contracts.Mapper
import com.example.truckercore.model.modules.employee.driver.data.Driver
import com.example.truckercore.model.modules.employee.driver.data.DriverDto

object DriverMapper: Mapper<Driver, DriverDto> {

    override fun toDto(entity: Driver): DriverDto {
        TODO("Not yet implemented")
    }

    override fun toEntity(dto: DriverDto): Driver {
        TODO("Not yet implemented")
    }
}