package com.example.truckercore.modules.employee.admin.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.employee.admin.aggregations.AdminWithDetails
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.AggregateAdminWithDetails
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.AggregatePersonalDataWithFilesUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class AggregateAdminWithDetailsImpl(
    private val getAdmin: GetAdminUseCase,
    private val getFile: GetFileUseCase,
    private val getPersonalDataWithFile: AggregatePersonalDataWithFilesUseCase
) : AggregateAdminWithDetails {

    override fun execute(
        documentParams: DocumentParameters
    ): Flow<Response<AdminWithDetails>> =
        combine(
            getAdmin.execute(documentParams),
            getFile.execute(getQueryParams(documentParams)),
            getPersonalDataWithFile.execute(getQueryParams(documentParams))
        ) { adminResult, fileResult, pDataResult ->
            if (adminResult !is Response.Success) return@combine Response.Empty

            val admin = adminResult.data
            val file = if (fileResult is Response.Success) fileResult.data.firstOrNull() else null
            val pData = if (pDataResult is Response.Success) pDataResult.data else emptyList()

            Response.Success(AdminWithDetails(admin, file, pData.toHashSet()))
        }

    private fun getQueryParams(adminParams: DocumentParameters) =
        QueryParameters.create(adminParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, adminParams.id))
            .setStream(adminParams.shouldStream)
            .build()

}