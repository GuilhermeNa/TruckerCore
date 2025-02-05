package com.example.truckercore.modules.fleet.shared.module.licensing.service

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingAggregation
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.GetStorageFileUseCase
import com.example.truckercore.shared.utils.expressions.handleUnexpectedError
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class LicensingServiceImpl(
    private val getLicensing: GetLicensingUseCase,
    private val getFile: GetStorageFileUseCase
) : LicensingService {

    override suspend fun getData(params: DocumentParameters): Flow<Response<Licensing>> =
        getLicensing.execute(params.user, params.id)

    override suspend fun getData(params: QueryParameters): Flow<Response<List<Licensing>>> =
        getLicensing.execute(params.user, params.queries)

    override suspend fun getWithAggregateData(
        params: DocumentParameters, aggregation: LicensingAggregation
    ): Flow<Response<LicensingWithFile>> = flow {

        val licensing = when (val licensingResponse = getLicencing(params)) {
            is Response.Success -> {
                licensingResponse.data
            }

            is Response.Empty -> {
                emit(handleLicensingEmptyResponse())
                return@flow
            }

            is Response.Error -> {
                emit(handleLicensingErrorResponse(licensingResponse))
                return@flow
            }
        }

        val files = if (aggregation.shouldGetFile()) {
            getFiles(params.user, licensing.id!!)
        } else emptyList()

        emit(Response.Success(LicensingWithFile(licensing, files)))

    }.catch {
        emit(it.handleUnexpectedError())
    }

    override suspend fun getWithAggregateData(
        params: QueryParameters, aggregation: LicensingAggregation
    ): Flow<Response<List<LicensingWithFile>>> = flow {
        mutableMapOf<String, List<StorageFile>>()

        val licensingList = when (val licensingResponse = getLicensing(params)) {
            is Response.Success -> licensingResponse.data

            is Response.Empty -> {
                emit(handleLicensingEmptyResponse())
                return@flow
            }

            is Response.Error -> {
                emit(handleLicensingErrorResponse(licensingResponse))
                return@flow
            }
        }

        val filesMap = if (aggregation.shouldGetFile()) {
            val licensingIds = licensingList.mapNotNull { it.id }
            val newFiles = getFiles(params.user, *licensingIds.toTypedArray())
            newFiles.groupBy { it.parentId }
        } else emptyMap()

        val result = licensingList.map {
            LicensingWithFile(licensing = it, files = filesMap[it.id] ?: emptyList())
        }

        emit(Response.Success(result))

    }.catch {
        emit(it.handleUnexpectedError())
    }

    private fun handleLicensingErrorResponse(errorResponse: Response.Error): Response.Error {
        logError(
            context = javaClass,
            exception = errorResponse.exception,
            message = "Some error occurred while searching for licensing."
        )
        return errorResponse
    }

    private fun handleLicensingEmptyResponse(): Response.Empty {
        logWarn(
            context = javaClass,
            message = "Licensing not found."
        )
        return Response.Empty
    }

    private suspend fun getLicencing(params: DocumentParameters): Response<Licensing> =
        getLicensing.execute(params.user, params.id).single()

    private suspend fun getLicensing(params: QueryParameters): Response<List<Licensing>> =
        getLicensing.execute(params.user, params.queries).single()

    private suspend fun getFiles(user: User, vararg licensingId: String): List<StorageFile> {
        val settings =
            listOf(QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, licensingId.asList()))

        return when (val response = getFile.execute(user, settings).single()) {
            is Response.Success -> response.data
            is Response.Empty -> {
                logWarn(
                    context = javaClass, message = "File not found for parentId: ${licensingId}."
                )
                emptyList()
            }

            is Response.Error -> {
                logError(
                    context = javaClass,
                    exception = response.exception,
                    message = response.exception.message.toString()
                )
                throw response.exception
            }
        }
    }

}