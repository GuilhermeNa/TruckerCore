package com.example.truckercore.model.modules.employee.admin.use_cases

import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore.model.modules.employee.admin.data.Admin
import com.example.truckercore.model.modules.employee.admin.data.AdminDto
import com.example.truckercore.model.modules.employee.admin.specification.AdminSpec
import com.example.truckercore.model.shared.utils.sealeds.AppResponse

interface GetAdminUseCase {

    suspend operator fun invoke(spec: AdminSpec): AppResponse<Admin>

}