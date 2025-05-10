package com.example.truckercore.model.modules.employee.admin.factory

import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.modules.employee._shared.EmployeeForm
import com.example.truckercore.model.modules.employee.admin.data.Admin
import com.example.truckercore.model.modules.employee.admin.data.AdminDto
import com.example.truckercore.model.modules.employee.admin.data.AdminID
import com.example.truckercore.model.shared.enums.Persistence

object AdminFactory {

    operator fun invoke(form: EmployeeForm): Admin {
        return Admin(
            id = AdminID(ID.generate()),
            companyId = form.companyId,
            name = form.name,
            email = form.email
        )
    }

}