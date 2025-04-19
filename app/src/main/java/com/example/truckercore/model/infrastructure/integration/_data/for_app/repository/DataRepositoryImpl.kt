package com.example.truckercore.model.infrastructure.integration._data.for_app.repository

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
        try {
            AppResponse.Success(dataSource.findOneBy(spec))
        } catch (e: Exception) {
            AppResponse.Error(appErrorFactory.handleDataSourceError(e))
        }

    override suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): AppResponse<List<T>> =
        try {
            val response = dataSource.findAllBy(spec)
            if (response.isEmpty()) AppResponse.Empty else AppResponse.Success(response)
        } catch (e: Exception) {
            AppResponse.Error(appErrorFactory.handleDataSourceError(e))
        }

    override fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<AppResponse<T>> =
        dataSource.flowOneBy(spec)
            .map { AppResponse.Success(it) }
            .catch { e -> AppResponse.Error(appErrorFactory.handleDataSourceError(e)) }

    override fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<AppResponse<List<T>>> =
        dataSource.flowAllBy(spec)
            .map { if (it.isEmpty()) AppResponse.Empty else AppResponse.Success(it) }
            .catch { e -> AppResponse.Error(appErrorFactory.handleDataSourceError(e)) }

}