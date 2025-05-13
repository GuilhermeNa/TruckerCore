package com.example.truckercore.model.infrastructure.integration.data.for_app.repository

import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSource
import com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors.DataAppErrorFactory
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification
import com.example.truckercore._utils.classes.AppResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataRepositoryImpl(
    private val dataSource: DataSource<*, *>,
    private val appErrorFactory: DataAppErrorFactory
) : DataRepository {

    companion object {
        private const val FIND_ONE_ERR_MSG =
            "Failed to find one entity by specification."
        private const val FIND_ALL_ERR_MSG =
            "Failed to find all entities by specification."
        private const val FLOW_ONE_ERR_MSG =
            "Failed to observe flow for a single entity by specification."
        private const val FLOW_ALL_ERR_MSG =
            "Failed to observe flow for all entities by specification."
    }

    override suspend fun <T : BaseDto> findOneBy(
        spec: Specification<T>
    ): AppResponse<T> = try {
        val data = dataSource.findById(spec)
        handleDataSourceData(data)
    } catch (e: Exception) {
        appErrorFactory("$FIND_ONE_ERR_MSG $spec", e)
    }

    override suspend fun <T : BaseDto> findAllBy(
        spec: Specification<T>
    ): AppResponse<List<T>> = try {
        val data = dataSource.findAllBy(spec)
        handleDataSourceData(data)
    } catch (e: Exception) {
        appErrorFactory("$FIND_ALL_ERR_MSG $spec", e)
    }

    override fun <T : BaseDto> flowOneBy(
        spec: Specification<T>
    ): Flow<AppResponse<T>> = dataSource.flowOneBy(spec)
        .map { handleDataSourceData(it) }
        .catch { emit(appErrorFactory("$FLOW_ONE_ERR_MSG $spec", it)) }

    override fun <T : BaseDto> flowAllBy(
        spec: Specification<T>
    ): Flow<AppResponse<List<T>>> = dataSource.flowAllBy(spec)
        .map { handleDataSourceData(it) }
        .catch { emit(appErrorFactory("$FLOW_ALL_ERR_MSG $spec", it)) }

    private fun <T> handleDataSourceData(data: T?): AppResponse<T> =
        if (data == null) AppResponse.Empty
        else AppResponse.Success(data)

}