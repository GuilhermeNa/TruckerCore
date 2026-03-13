package com.example.truckercore.layers.data_2.remote.interfaces

import com.example.truckercore.layers.data.base.dto.impl.CompanyDto
import com.example.truckercore.layers.domain.base.ids.CompanyID

interface CompanyRemoteDataSource {

    suspend fun fetch(companyId: CompanyID): CompanyDto?

}