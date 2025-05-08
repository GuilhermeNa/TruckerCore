package com.example.truckercore.model.modules.employee.admin.factory

import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.modules.employee._shared.EmployeeForm
import com.example.truckercore.model.modules.employee.admin.data.AdminDto
import com.example.truckercore.model.shared.enums.Persistence

object AdminFactory {

    operator fun invoke(form: EmployeeForm): AdminDto {
        return AdminDto(
            id = ID.generate(),
            companyId = form.companyId.value,
            persistence = Persistence.ACTIVE,
            name = form.name.value,
            email = form.email.value
        )
    }

}