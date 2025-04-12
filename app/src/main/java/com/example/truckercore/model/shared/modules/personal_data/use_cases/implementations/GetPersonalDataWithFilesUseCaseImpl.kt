package com.example.truckercore.model.shared.modules.personal_data.use_cases.implementations

import com.example.truckercore.model.configs.app_constants.Field
import com.example.truckercore.model.shared.enums.QueryType
import com.example.truckercore.model.shared.modules.file.entity.File
import com.example.truckercore.model.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.model.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.model.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataUseCase
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataWithFilesUseCase
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.parameters.QuerySettings
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

internal class GetPersonalDataWithFilesUseCaseImpl(
    private val getPersonalData: GetPersonalDataUseCase,
    private val getFile: GetFileUseCase
) : GetPersonalDataWithFilesUseCase {

    override fun execute(documentParams: DocumentParameters): Flow<Response<PersonalDataWithFile>> =
        combine(
            getPersonalData.execute(documentParams),
            getFile.execute(getFileQueryParams(documentParams))
        ) { pDataResponse, fileResponse ->
            if (pDataResponse !is Response.Success) return@combine Response.Empty

            val files = fileResponse.extractFiles()
            val pData = pDataResponse.data
            val result = PersonalDataWithFile(pData, files)

            Response.Success(result)
        }

    private fun Response<List<File>>.extractFiles() =
        if (this is Response.Success) this.data else emptyList()

    private fun getFileQueryParams(documentParams: DocumentParameters) =
        QueryParameters.create(documentParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, documentParams.id))
            .setStream(documentParams.shouldStream)
            .build()

    //----------------------------------------------------------------------------------------------

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(queryParams: QueryParameters): Flow<Response<List<PersonalDataWithFile>>> =
        getPersonalData.execute(queryParams).flatMapConcat { pDataResponse ->
            if (pDataResponse !is Response.Success) return@flatMapConcat flowOf(Response.Empty)

            val pData = pDataResponse.data

            getFile.execute(
                getFilesQueryParams(queryParams, pData.mapNotNull { it.id })
            ).map { filesResponse ->
                val filesMap = filesResponse.groupFiles()
                val result = getResult(pData, filesMap)
                Response.Success(result)
            }
        }

    private fun getResult(
        pData: List<PersonalData>,
        filesMap: Map<String, List<File>>
    ): List<PersonalDataWithFile> {
        val result = pData.map { lic ->
            PersonalDataWithFile(pData = lic, files = filesMap[lic.id] ?: emptyList())
        }
        return result
    }

    private fun Response<List<File>>.groupFiles(): Map<String, List<File>> =
        if (this is Response.Success) this.data.groupBy { it.parentId }
        else emptyMap()

    private fun getFilesQueryParams(queryParams: QueryParameters, pDataIds: List<String>) =
        QueryParameters.create(queryParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, pDataIds))
            .setStream(queryParams.shouldStream)
            .build()

}