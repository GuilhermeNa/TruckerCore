package com.example.truckercore.model.modules.employee.autonomous.data

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.modules._contracts.Entity
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.employee._contracts.Employee
import com.example.truckercore.model.shared.enums.Persistence

data class Autonomous(
    override val id: AutID,
    override val companyId: CompanyID,
    override val persistence: Persistence,
    override val name: FullName,
    override val email: Email? = null
): Entity, Employee
