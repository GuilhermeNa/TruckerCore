package com.example.truckercore.modules.person.employee.admin.service

import com.example.truckercore.shared.abstractions.Service
import com.example.truckercore.infrastructure.util.ExceptionHandler
import com.example.truckercore.modules.person.employee.admin.aggregations.AdminWithDetails
import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.AggregateAdminWithDetails
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class AdminServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val getAdmin: GetAdminUseCase,
    private val getAdminWithDetails: AggregateAdminWithDetails
): Service(exceptionHandler), AdminService {

    override fun fetchAdmin(
        documentParam: DocumentParameters
    ): Flow<Response<Admin>> = runSafe { getAdmin.execute(documentParam) }

    override fun fetchAdminWithDetails(
        documentParam: DocumentParameters
    ): Flow<Response<AdminWithDetails>> = runSafe { getAdminWithDetails.execute(documentParam) }

}