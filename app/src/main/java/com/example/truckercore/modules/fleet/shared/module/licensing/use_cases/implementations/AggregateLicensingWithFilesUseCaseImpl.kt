package com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.GetStorageFileUseCase
import com.example.truckercore.shared.utils.expressions.handleUnexpectedError
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

internal class AggregateLicensingWithFilesUseCaseImpl(
    private val getSingleLicensingWithFiles: SingleLicensingWithFileUseCase,
    private val getListLicensingWithFiles: ListLicensingWithFileUseCase
) : AggregateLicensingWithFilesUseCase {

    override fun execute(documentParams: DocumentParameters) =
        getSingleLicensingWithFiles.execute(documentParams)

    override fun execute(queryParams: QueryParameters) =
        getListLicensingWithFiles.execute(queryParams)

}

//---

internal class SingleLicensingWithFileUseCase(
    private val getLicensing: GetLicensingUseCase,
    private val getFile: GetStorageFileUseCase
) {

    fun execute(documentParams: DocumentParameters): Flow<Response<LicensingWithFile>> = flow {
        combine(
            getLicensingFlow(documentParams),
            getFileFlow(documentParams)
        ) { licensingResponse, fileResponse ->
            handleCombinedResponse(licensingResponse, fileResponse)
        }.catch { exception ->
            emit(exception.handleUnexpectedError())
        }.collect { result -> emit(result) }
    }

    private suspend fun getLicensingFlow(documentParams: DocumentParameters): Flow<Response<Licensing>> {
        return getLicensing.execute(documentParams)
    }

    private suspend fun getFileFlow(documentParams: DocumentParameters): Flow<Response<List<StorageFile>>> {
        val queryParams = QueryParameters.create(documentParams.user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, documentParams.id))
            .build()
        return getFile.execute(queryParams)
    }

    private fun handleCombinedResponse(
        licensingResponse: Response<Licensing>,
        fileResponse: Response<List<StorageFile>>
    ): Response<LicensingWithFile> {
        return when {
            licensingResponse is Response.Error -> Response.Error(licensingResponse.exception)
            licensingResponse is Response.Empty -> Response.Empty
            fileResponse is Response.Error -> Response.Error(fileResponse.exception)
            else -> combineLicensingWithFile(licensingResponse as Response.Success, fileResponse)
        }
    }

    private fun combineLicensingWithFile(
        licensingResponse: Response.Success<Licensing>,
        fileResponse: Response<List<StorageFile>>
    ): Response<LicensingWithFile> {
        val files = if (fileResponse is Response.Success) fileResponse.data else emptyList()
        return Response.Success(LicensingWithFile(licensingResponse.data, files))
    }

}

//---

internal class ListLicensingWithFileUseCase(
    private val getLicensing: GetLicensingUseCase,
    private val getFile: GetStorageFileUseCase
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun execute(queryParams: QueryParameters): Flow<Response<List<LicensingWithFile>>> =
        flow {
            getLicensingFlow(queryParams).flatMapConcat { licensing ->
                handleLicensingResponse(licensing, queryParams.user)
            }.catch { exception -> emit(exception.handleUnexpectedError()) }
                .collect { result -> emit(result) }
        }

    private suspend fun getLicensingFlow(queryParams: QueryParameters): Flow<Response<List<Licensing>>> {
        return getLicensing.execute(queryParams)
    }

    private suspend fun getFilesFlow(user: User, licensingList: List<Licensing>): Flow<Response<List<StorageFile>>> {
        val filesQueryParams = QueryParameters.create(user)
            .setQueries(
                QuerySettings(
                    Field.PARENT_ID,
                    QueryType.WHERE_IN,
                    licensingList.mapNotNull { it.id })
            )
            .build()
        return getFile.execute(filesQueryParams)
    }

    private suspend fun handleLicensingResponse(
        licensingResponse: Response<List<Licensing>>,
        user: User
    ): Flow<Response<List<LicensingWithFile>>> {
        return when (licensingResponse) {
            is Response.Error -> flowOf(Response.Error(licensingResponse.exception))
            is Response.Empty -> flowOf(Response.Empty)
            is Response.Success ->
                getFilesFlow(user, licensingResponse.data).map { fileResponse ->
                    if (fileResponse is Response.Error) return@map Response.Error(fileResponse.exception)
                    combineLicensingWithFiles(licensingResponse, fileResponse)
                }
        }
    }

    private fun combineLicensingWithFiles(
        licensingResponse: Response.Success<List<Licensing>>,
        fileResponse: Response<List<StorageFile>>
    ): Response<List<LicensingWithFile>> {
        val filesMap =
            if (fileResponse is Response.Success) fileResponse.data.groupBy { it.parentId } else emptyMap()
        val combinedData = licensingResponse.data.map { licensing ->
            LicensingWithFile(
                licensing = licensing,
                files = filesMap[licensing.id] ?: emptyList()
            )
        }
        return Response.Success(combinedData)
    }

}