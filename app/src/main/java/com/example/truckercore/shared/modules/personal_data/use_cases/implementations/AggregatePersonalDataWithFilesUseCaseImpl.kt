package com.example.truckercore.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.AggregatePersonalDataWithFilesUseCase
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataUseCase
import com.example.truckercore.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

internal class AggregatePersonalDataWithFilesUseCaseImpl(
    private val getPersonalData: GetPersonalDataUseCase,
    private val getFile: GetFileUseCase
) : AggregatePersonalDataWithFilesUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<PersonalDataWithFile>> =
        getSinglePersonalDataWithFilesFlow(documentParams)

    private fun getSinglePersonalDataWithFilesFlow(documentParams: DocumentParameters) = combine(
        getPersonalData.execute(documentParams),
        getFile.execute(getDocumentParams(documentParams))
    ) { pDataResponse, fileResponse ->

        if (pDataResponse !is Response.Success) return@combine Response.Empty

        val files = if (fileResponse is Response.Success) fileResponse.data else emptyList()
        val pData = pDataResponse.data

        Response.Success(PersonalDataWithFile(pData, files))
    }

    private fun getDocumentParams(documentParams: DocumentParameters) =
        QueryParameters.create(documentParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, documentParams.id))
            .setStream(documentParams.shouldStream)
            .build()

    //----------------------------------------------------------------------------------------------

    override fun execute(queryParams: QueryParameters): Flow<Response<List<PersonalDataWithFile>>> =
        getListOfPersonalDataWithFilesFlow(queryParams)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getListOfPersonalDataWithFilesFlow(queryParams: QueryParameters) =
        getPersonalData.execute(queryParams).flatMapConcat { pDataResponse ->
            if (pDataResponse !is Response.Success) return@flatMapConcat flowOf(Response.Empty)
            val pData = pDataResponse.data

            getFile.execute(
                getFilesQueryParams(queryParams, pData.mapNotNull { it.id })
            ).map { filesResponse ->
                val filesMap = if (filesResponse is Response.Success) {
                    filesResponse.data.groupBy { it.parentId }
                } else emptyMap()

                val result = pData.map { lic ->
                    PersonalDataWithFile(pData = lic, files = filesMap[lic.id] ?: emptyList())
                }

                Response.Success(result)
            }
        }

    private fun getFilesQueryParams(queryParams: QueryParameters, pDataIds: List<String>) =
        QueryParameters.create(queryParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, pDataIds))
            .setStream(queryParams.shouldStream)
            .build()

}