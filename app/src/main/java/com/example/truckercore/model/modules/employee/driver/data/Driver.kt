package com.example.truckercore.model.modules.employee.driver.data

import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore.model.modules.company.data_helper.CompanyID
import com.example.truckercore.model.modules.employee.driver.data_helper.DriverID
import com.example.truckercore.model.shared.enums.Persistence
import com.example.truckercore.model.shared.interfaces.data.entity.EmployeeEntity

data class Driver(
    override val id: DriverID,
    override val name: FullName,
    override val email: Email,
    override val companyId: CompanyID,
    override val persistence: Persistence,
): EmployeeEntity