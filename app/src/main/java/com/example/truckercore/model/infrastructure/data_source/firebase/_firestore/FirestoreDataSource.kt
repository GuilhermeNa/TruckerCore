package com.example.truckercore.model.infrastructure.data_source.firebase._firestore

import com.example.truckercore.model.modules.user.Specification
import com.example.truckercore.model.shared.interfaces.data.dto.BaseDto
import kotlinx.coroutines.flow.Flow

interface FirestoreDataSource {

    suspend fun <T : BaseDto> findOneBy(spec: Specification<T>): T

    suspend fun <T : BaseDto> findAllBy(spec: Specification<T>): List<T>

    fun <T : BaseDto> flowOneBy(spec: Specification<T>): Flow<T>

    fun <T : BaseDto> flowAllBy(spec: Specification<T>): Flow<List<T>>

}