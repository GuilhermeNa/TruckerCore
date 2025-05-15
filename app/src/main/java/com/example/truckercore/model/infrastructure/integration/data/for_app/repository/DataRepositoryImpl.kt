package com.example.truckercore.model.infrastructure.integration.data.for_app.repository

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore.model.infrastructure.integration.data.for_api.DataSource
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataRepositoryImpl(
    private val dataSource: DataSource,
    private val errorFactory: DataRepositoryErrorFactory
) : DataRepository {

    override suspend fun <T : BaseDto> findOneBy(spec: Specification<T>): AppResponse<T> = try {
        val data = dataSource.findById(spec)
        handleDataSourceData(data)
    } catch (e: Exception) {
        val appError = errorFactory.findingOne(spec, e)
        AppResponse.Error(appError)
    }

    override suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): AppResponse<List<T>> =
        try {
            val data = dataSource.findAllBy(spec)
            handleDataSourceData(data)
        } catch (e: Exception) {
            val appError = errorFactory.findingAll(spec, e)
            AppResponse.Error(appError)
        }

    override fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<AppResponse<T>> =
        dataSource.flowOneBy(spec)
            .map { handleDataSourceData(it) }
            .catch {
                val appError = errorFactory.flowingOne(spec, it)
                emit(AppResponse.Error(appError))
            }

    override fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<AppResponse<List<T>>> =
        dataSource.flowAllBy(spec)
            .map { handleDataSourceData(it) }
            .catch {
                val appError = errorFactory.flowingAll(spec, it)
                emit(AppResponse.Error(appError))
            }

    private fun <T> handleDataSourceData(data: T?): AppResponse<T> =
        if (data == null) AppResponse.Empty
        else AppResponse.Success(data)

}