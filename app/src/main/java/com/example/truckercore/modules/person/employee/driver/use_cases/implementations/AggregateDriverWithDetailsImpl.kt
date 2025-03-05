package com.example.truckercore.modules.person.employee.driver.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.person.employee.driver.aggregations.DriverWithDetails
import com.example.truckercore.modules.person.employee.driver.use_cases.interfaces.AggregateDriverWithDetails
import com.example.truckercore.modules.person.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.AggregatePersonalDataWithFilesUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class AggregateDriverWithDetailsImpl(
    private val getDriver: GetDriverUseCase,
    private val getFile: GetFileUseCase,
    private val getPersonalDataWithFile: AggregatePersonalDataWithFilesUseCase
) : AggregateDriverWithDetails {

    override fun execute(documentParams: DocumentParameters): Flow<Response<DriverWithDetails>> =
        combine(
            getDriver.execute(documentParams),
            getFile.execute(getAggregateParams(documentParams)),
            getPersonalDataWithFile.execute(getAggregateParams(documentParams))
        ) { driverResult, fileResult, pDataResult ->
            if (driverResult !is Response.Success) return@combine Response.Empty

            val driver = driverResult.data
            val file = if (fileResult is Response.Success) fileResult.data.firstOrNull() else null
            val pData = if (pDataResult is Response.Success) pDataResult.data else emptyList()

            Response.Success(DriverWithDetails(driver, file, pData.toHashSet()))
        }

    private fun getAggregateParams(driverParams: DocumentParameters) =
        QueryParameters.create(driverParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, driverParams.id))
            .setStream(driverParams.shouldStream)
            .build()

}