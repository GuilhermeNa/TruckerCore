package com.example.truckercore.model.modules.person.employee.admin.service

import com.example.truckercore.model.infrastructure.util.ExceptionHandler
import com.example.truckercore.model.modules.person.employee.admin.entity.Admin
import com.example.truckercore.model.modules.person.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.model.modules.person.shared.person_details.GetPersonWithDetailsUseCase
import com.example.truckercore.model.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.model.modules.user.enums.PersonCategory
import com.example.truckercore.model.shared.abstractions.Service
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class AdminServiceImpl(
    override val exceptionHandler: ExceptionHandler,
    private val getAdmin: GetAdminUseCase,
    private val getPersonWithDetails: GetPersonWithDetailsUseCase
) : Service(exceptionHandler), AdminService {

    override fun fetchAdmin(
        documentParam: DocumentParameters
    ): Flow<Response<Admin>> = runSafe { getAdmin.execute(documentParam) }

    override fun fetchAdminWithDetails(
        documentParam: DocumentParameters
    ): Flow<Response<PersonWithDetails>> = runSafe {
        getPersonWithDetails.execute(documentParam, PersonCategory.ADMIN)
    }

}