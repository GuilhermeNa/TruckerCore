package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.GetStorageFileUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

internal class AggregateLicensingWithFilesUseCaseImpl(
    private val getLicensing: GetLicensingUseCase,
    private val getFile: GetStorageFileUseCase
) : AggregateLicensingWithFilesUseCase {

    override fun execute(documentParams: DocumentParameters) =
        getSingleLicensingWithFilesFlow(documentParams)

    private fun getSingleLicensingWithFilesFlow(documentParams: DocumentParameters) = combine(
        getLicensing.execute(documentParams),
        getFile.execute(getQueryParams(documentParams))
    ) { licensingResponse, fileResponse ->

        if (licensingResponse !is Response.Success) return@combine Response.Empty

        val files = if (fileResponse is Response.Success) fileResponse.data else emptyList()
        val licensing = licensingResponse.data

        Response.Success(LicensingWithFile(licensing, files))
    }

    private fun getQueryParams(documentParams: DocumentParameters) =
        QueryParameters.create(documentParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, documentParams.id))
            .setStream(documentParams.shouldStream)
            .build()

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters) =
        getListOfLicensingWithFilesFlow(queryParams)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getListOfLicensingWithFilesFlow(queryParams: QueryParameters) =
        getLicensing.execute(queryParams).flatMapConcat { licensingResponse ->
            if (licensingResponse !is Response.Success) return@flatMapConcat flowOf(Response.Empty)
            val licensing = licensingResponse.data

            getFile.execute(
                getFilesQueryParams(queryParams, licensing.mapNotNull { it.id })
            ).map { filesResponse ->
                val filesMap = if (filesResponse is Response.Success) {
                    filesResponse.data.groupBy { it.parentId }
                } else emptyMap()

                val result = licensing.map { lic ->
                    LicensingWithFile(licensing = lic, files = filesMap[lic.id] ?: emptyList())
                }

                Response.Success(result)
            }
        }

    private fun getFilesQueryParams(queryParams: QueryParameters, licensingIds: List<String>) =
        QueryParameters.create(queryParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, licensingIds))
            .setStream(queryParams.shouldStream)
            .build()

}
