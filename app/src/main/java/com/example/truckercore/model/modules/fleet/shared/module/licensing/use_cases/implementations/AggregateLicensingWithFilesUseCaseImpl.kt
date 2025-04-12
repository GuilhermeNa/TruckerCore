package com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.model.configs.app_constants.Field
import com.example.truckercore.model.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.model.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.model.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.model.shared.enums.QueryType
import com.example.truckercore.model.shared.modules.file.entity.File
import com.example.truckercore.model.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.parameters.QuerySettings
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

internal class AggregateLicensingWithFilesUseCaseImpl(
    private val getLicensing: GetLicensingUseCase,
    private val getFile: GetFileUseCase
) : AggregateLicensingWithFilesUseCase {

    override fun execute(documentParams: DocumentParameters) = combine(
        getLicensing.execute(documentParams),
        getFile.execute(getQueryParams(documentParams))
    ) { licensingResponse, fileResponse ->
        if (licensingResponse !is AppResponse.Success) return@combine AppResponse.Empty

        val files = if (fileResponse is AppResponse.Success) fileResponse.data else emptyList()
        val licensing = licensingResponse.data

        AppResponse.Success(LicensingWithFile(licensing, files))
    }

    private fun getQueryParams(documentParams: DocumentParameters) =
        QueryParameters.create(documentParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, documentParams.id))
            .setStream(documentParams.shouldStream)
            .build()

    //----------------------------------------------------------------------------------------------

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(queryParams: QueryParameters) =
        getLicensing.execute(queryParams).flatMapConcat { licensingResponse ->
            if (licensingResponse !is AppResponse.Success) return@flatMapConcat flowOf(AppResponse.Empty)

            val licensing = licensingResponse.data
            val licensingIds = licensing.mapNotNull { it.id }
            val filesQueryParams = getFilesQueryParams(queryParams, licensingIds)

            getFile.execute(filesQueryParams).map { filesResponse ->
                val filesMap = getFilesMap(filesResponse)
                val result = getResult(licensing, filesMap)
                AppResponse.Success(result)
            }
        }

    private fun getResult(
        licensing: List<Licensing>, filesMap: Map<String, List<File>>
    ) = licensing.map { lic ->
        LicensingWithFile(licensing = lic, files = filesMap[lic.id] ?: emptyList())
    }

    private fun getFilesMap(filesResponse: AppResponse<List<File>>) =
        if (filesResponse is AppResponse.Success) {
            filesResponse.data.groupBy { it.parentId }
        } else emptyMap()

    private fun getFilesQueryParams(queryParams: QueryParameters, licensingIds: List<String>) =
        QueryParameters.create(queryParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, licensingIds))
            .setStream(queryParams.shouldStream)
            .build()

}
