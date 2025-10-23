package com.example.truckercore.layers.domain.model.employee._shared.factory

import com.example.truckercore.data.infrastructure.security.data.enums.Role
import com.example.truckercore.data.modules.employee.admin.factory.AdminFactory
import com.example.truckercore.data.modules.employee.autonomous.factory.AutonomousFactory
import com.example.truckercore.data.modules.employee.driver.factory.DriverFactory

object EmployeeFactory {

    fun withSystemAccess(form: EmployeeForm): com.example.truckercore.domain.model.employee._shared.contracts.Employee {
        return when (form.role) {
            Role.ADMIN -> AdminFactory.registered(form)
            Role.DRIVER -> DriverFactory.registered(form)
            Role.AUTONOMOUS -> AutonomousFactory.registered(form)
            Role.MANAGER -> throw com.example.truckercore.core.error.classes.technical.TechnicalException.NotImplemented()
        }
    }

}