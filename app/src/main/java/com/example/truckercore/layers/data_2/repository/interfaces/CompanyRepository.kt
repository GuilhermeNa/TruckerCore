package com.example.truckercore.layers.data_2.repository.interfaces

import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.model.company.Company
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {

    suspend fun fetch(id: CompanyID): DataOutcome<Company>

    fun observe(id: CompanyID): Flow<DataOutcome<Company>>

    suspend fun save(company: Company): OperationOutcome


}