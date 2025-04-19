package com.example.truckercore.model.infrastructure.integration._data.for_app.repository

import com.example.truckercore.model.infrastructure.integration._data.for_app.specification.Specification
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    suspend fun <T : BaseDto> findOneBy(spec: Specification<T>): AppResponse<T>

    suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): AppResponse<List<T>>

    fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<AppResponse<T>>

    fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<AppResponse<List<T>>>

}