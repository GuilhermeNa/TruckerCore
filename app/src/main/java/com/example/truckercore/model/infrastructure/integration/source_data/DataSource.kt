package com.example.truckercore.model.infrastructure.integration.source_data

import com.example.truckercore.model.infrastructure.integration.source_data.specification.Specification
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import kotlinx.coroutines.flow.Flow

interface DataSource {

    suspend fun <T : BaseDto> findOneBy(spec: Specification<T>): T

    suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): List<T>

    fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<T>

    fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<List<T>>

}