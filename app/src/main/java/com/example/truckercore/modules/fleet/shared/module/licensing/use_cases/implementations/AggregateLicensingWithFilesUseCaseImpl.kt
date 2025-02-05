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
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class AggregateLicensingWithFilesUseCaseImpl(
    private val getLicensing: GetLicensingUseCase,
    private val getFile: GetStorageFileUseCase
) : AggregateLicensingWithFilesUseCase {

    override suspend fun execute(user: User, id: String) = flow {
        coroutineScope {
            val (licensingResponse, filesResponse) = awaitAll(
                fetchLicensingAsync(user, id),
                fetchFilesAsync(user, id)
            )

            val licensing = when (licensingResponse) {
                is Response.Success -> licensingResponse.data as Licensing

                is Response.Empty -> {
                    emit(Response.Empty)
                    return@coroutineScope
                }

                is Response.Error -> {
                    emit(licensingResponse)
                    return@coroutineScope
                }
            }

            val files = when (filesResponse) {
                is Response.Success -> filesResponse.data as List<StorageFile>

                is Response.Empty -> emptyList()

                is Response.Error -> {
                    emit(filesResponse)
                    return@coroutineScope
                }
            }

            emit(Response.Success(LicensingWithFile(licensing, files)))

        }
    }.catch {
        emit(it.handleUnexpectedError())
    }

    private fun CoroutineScope.fetchLicensingAsync(user: User, id: String) = async {
        getLicensing.execute(user, id).single()
    }

    private fun CoroutineScope.fetchFilesAsync(user: User, parentId: String) = async {
        getFile.execute(user, QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, parentId))
            .single()
    }

    override suspend fun execute(user: User, vararg querySettings: QuerySettings):
            Flow<Response<List<LicensingWithFile>>> = flow {
        val licensing = when (
            val response = fetchLicensing(user, *querySettings)
        ) {
            is Response.Success -> response.data

            is Response.Error -> {
                emit(response)
                return@flow
            }

            is Response.Empty -> {
                emit(response)
                return@flow
            }
        }

        val filesMap = when (
            val response = fetchFilesForLicensing(user, licensing.mapNotNull { it.id })
        ) {
            is Response.Success -> response.data.groupBy { it.parentId }

            is Response.Empty -> emptyMap()

            is Response.Error -> {
                emit(response)
                return@flow
            }
        }

        val result = getListOfLicensingWithItsFiles(licensing, filesMap)
        emit(Response.Success(result))

    }.catch {
        emit(it.handleUnexpectedError())
    }

    private fun getListOfLicensingWithItsFiles(
        licensing: List<Licensing>, filesMap: Map<String, List<StorageFile>>
    ) = licensing.map { lic ->
        val licFiles = filesMap[lic.id] ?: emptyList()
        LicensingWithFile(lic, licFiles)
    }

    private suspend fun fetchLicensing(user: User, vararg querySettings: QuerySettings) =
        getLicensing.execute(user, *querySettings).single()

    private suspend fun fetchFilesForLicensing(user: User, parentIds: List<String>) =
        getFile.execute(
            user, QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, parentIds)
        ).single()

}