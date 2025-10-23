package com.example.truckercore.layers.domain.model.employee.admin.use_cases

import com.example.truckercore.data.modules.employee.admin.data.Admin
import com.example.truckercore.data.shared.outcome.data.DataOutcome

interface GetAdminUseCase {

    suspend operator fun invoke(spec: com.example.truckercore.domain.model.employee.admin.specification.AdminSpec): DataOutcome<Admin>

}