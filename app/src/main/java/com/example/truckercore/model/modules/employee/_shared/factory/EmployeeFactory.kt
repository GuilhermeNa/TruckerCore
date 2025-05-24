package com.example.truckercore.model.modules.employee._shared.factory

import com.example.truckercore.model.errors.technical.TechnicalException
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.modules.employee._shared.contracts.Employee
import com.example.truckercore.model.modules.employee.admin.factory.AdminFactory
import com.example.truckercore.model.modules.employee.autonomous.factory.AutonomousFactory
import com.example.truckercore.model.modules.employee.driver.factory.DriverFactory

object EmployeeFactory {

    fun withSystemAccess(form: EmployeeForm): Employee {
        return when (form.role) {
            Role.ADMIN -> AdminFactory.registered(form)
            Role.DRIVER -> DriverFactory.registered(form)
            Role.AUTONOMOUS -> AutonomousFactory.registered(form)
            Role.MANAGER -> throw TechnicalException.NotImplemented()
        }
    }

}