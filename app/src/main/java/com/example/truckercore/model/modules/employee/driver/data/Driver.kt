package com.example.truckercore.model.modules.employee.driver.data

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.modules._contracts.Entity
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.employee.driver.data_helper.DriverID
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.modules.employee._contracts.Employee

data class Driver(
    override val id: DriverID,
    override val name: FullName,
    override val email: Email? = null,
    override val companyId: CompanyID,
    override val persistence: Persistence
): Entity, Employee