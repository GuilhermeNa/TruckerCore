package com.example.truckercore.model.modules.employee._shared

import com.example.truckercore.model.errors.exceptions.TechnicalException
import com.example.truckercore.model.modules.employee._contracts.Employee
import com.example.truckercore.model.modules.employee._contracts.EmployeeDto
import com.example.truckercore.model.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.model.modules.employee.admin.data.Admin
import com.example.truckercore.model.modules.employee.autonomous.data.Autonomous
import com.example.truckercore.model.modules.employee.autonomous.mapper.AutonomousMapper
import com.example.truckercore.model.modules.employee.driver.data.Driver
import com.example.truckercore.model.modules.employee.driver.mapper.DriverMapper

object EmployeeMapper {

    fun toDto(employee: Employee): EmployeeDto {
        return when (employee) {
            is Admin -> AdminMapper.toDto(employee)
            is Driver -> DriverMapper.toDto(employee)
            is Autonomous -> AutonomousMapper.toDto(employee)

            else -> throw TechnicalException.NotImplemented(
                "Mapping not implemented for employee type: ${employee::class.simpleName}"
            )
        }
    }

}