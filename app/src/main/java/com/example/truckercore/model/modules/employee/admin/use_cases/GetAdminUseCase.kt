package com.example.truckercore.model.modules.employee.admin.use_cases

import com.example.truckercore.model.modules.employee.admin.data.Admin
import com.example.truckercore.model.modules.employee.admin.specification.AdminSpec
import com.example.truckercore._utils.classes.AppResponse

interface GetAdminUseCase {

    suspend operator fun invoke(spec: AdminSpec): AppResponse<Admin>

}