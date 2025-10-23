package com.example.truckercore.layers.domain.model.employee._shared

import com.example.truckercore.data.modules.employee._shared.contracts.EmployeeDto
import com.example.truckercore.data.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.data.modules.employee.admin.data.Admin
import com.example.truckercore.data.modules.employee.autonomous.data.Autonomous
import com.example.truckercore.data.modules.employee.autonomous.mapper.AutonomousMapper
import com.example.truckercore.data.modules.employee.driver.data.Driver
import com.example.truckercore.data.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.layers.domain.model.employee._shared.contracts.Employee

object EmployeeMapper {

    fun toDto(employee: Employee): EmployeeDto {
        return when (employee) {
            is Admin -> AdminMapper.toDto(employee)
            is Driver -> DriverMapper.toDto(employee)
            is Autonomous -> AutonomousMapper.toDto(employee)

            else -> throw com.example.truckercore.core.error.classes.technical.TechnicalException.NotImplemented(
                "Mapping not implemented for employee type: ${employee::class.simpleName}"
            )
        }
    }

}