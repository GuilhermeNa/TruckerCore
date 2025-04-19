package com.example.truckercore.model.infrastructure.integration._data.for_api

import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.Specification
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import kotlinx.coroutines.flow.Flow

abstract class DataSource(
    protected val interpreter: DataSourceInterpreter,
    protected val errorMapper: DataSourceErrorMapper
) {

    abstract suspend fun <T : BaseDto> findOneBy(spec: Specification<T>): T?

    abstract suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): List<T>?

    abstract fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<T?>

    abstract fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<List<T>?>

}