package com.example.truckercore.layers.data_2.remote.interfaces

import com.example.truckercore.layers.data.base.dto.impl.CompanyDto
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSource
import com.example.truckercore.layers.domain.base.ids.CompanyID
import kotlinx.coroutines.flow.Flow

interface CompanyRemoteDataSource : RemoteDataSource {

    suspend fun fetch(id: CompanyID): CompanyDto?

    fun observe(id: CompanyID): Flow<CompanyDto?>

    suspend fun save(dto: CompanyDto)

}