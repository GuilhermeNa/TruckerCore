package com.example.truckercore.layers.data_2.repository.impl

import com.example.truckercore.layers.data.base.mapper.impl.CompanyMapper
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data_2.remote.interfaces.CompanyRemoteDataSource
import com.example.truckercore.layers.data_2.repository.base.DataRepositoryBase
import com.example.truckercore.layers.data_2.repository.interfaces.CompanyRepository
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.model.company.Company

class CompanyRepositoryImpl(
    override val remote: CompanyRemoteDataSource
) : DataRepositoryBase(remote), CompanyRepository {

    override suspend fun fetch(id: CompanyID): DataOutcome<Company> =
        fetch(
            data = remote.fetch(id),
            mapper = { CompanyMapper.toEntity(it) }
        )

    override suspend fun save(company: Company): OperationOutcome {
        TODO("Not yet implemented")
    }

}