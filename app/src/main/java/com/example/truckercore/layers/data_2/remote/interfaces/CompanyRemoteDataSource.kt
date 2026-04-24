package com.example.truckercore.layers.data_2.remote.interfaces

import com.example.truckercore.layers.data.base.dto.impl.CompanyDto
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSource
import com.example.truckercore.layers.domain.base.ids.CompanyID

interface CompanyRemoteDataSource : RemoteDataSource {

    suspend fun fetch(id: CompanyID): CompanyDto?

    suspend fun save(dto: CompanyDto): OperationOutcome

}