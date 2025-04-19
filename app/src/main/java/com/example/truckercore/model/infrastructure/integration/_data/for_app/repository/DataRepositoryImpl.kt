package com.example.truckercore.model.infrastructure.integration._data.for_app.repository

import com.example.truckercore.model.infrastructure.integration._data.for_api.DataSource
import com.example.truckercore.model.infrastructure.integration._data.for_app.app_exception.DataAppErrorFactory
import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.Specification
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataRepositoryImpl(
    private val dataSource: DataSource,
    private val appErrorFactory: DataAppErrorFactory
) : DataRepository {

    override suspend fun <T : BaseDto> findOneBy(spec: Specification<T>): AppResponse<T> =
        findSafe { dataSource.findById(spec) }

    override suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): AppResponse<List<T>> =
        findSafe { dataSource.findAllBy(spec) }

    override fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<AppResponse<T>> =
        flowSafe { dataSource.flowOneBy(spec) }

    override fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<AppResponse<List<T>>> =
        flowSafe { dataSource.flowAllBy(spec) }

    //----------------------------------------------------------------------------------------------
    private inline fun <T> findSafe(block: () -> T?): AppResponse<T> = try {
        handleDataSourceData(block())
    } catch (e: Exception) {
        handleDataSourceException(e)
    }

    private inline fun <T> flowSafe(block: () -> Flow<T?>): Flow<AppResponse<T>> =
        block().map { handleDataSourceData(it) }
            .catch { handleDataSourceException(it) }

    private fun handleDataSourceException(e: Throwable) =
        AppResponse.Error(appErrorFactory(e))

    private fun <T> handleDataSourceData(data: T?): AppResponse<T> =
        if (data == null) AppResponse.Empty
        else AppResponse.Success(data)

}