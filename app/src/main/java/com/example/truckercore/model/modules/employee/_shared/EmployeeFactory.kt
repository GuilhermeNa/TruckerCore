package com.example.truckercore.model.modules.employee._shared

import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.modules.employee._contracts.Employee
import com.example.truckercore.model.modules.employee.admin.factory.AdminFactory
import com.example.truckercore.model.modules.employee.autonomous.factory.AutonomousFactory

object EmployeeFactory {

    operator fun invoke(form: EmployeeForm): Employee {
        return when (form.role) {
            Role.ADMIN -> AdminFactory(form)
            Role.AUTONOMOUS -> AutonomousFactory(form)
            Role.MANAGER -> TODO()
            Role.DRIVER -> TODO()
        }
    }

}