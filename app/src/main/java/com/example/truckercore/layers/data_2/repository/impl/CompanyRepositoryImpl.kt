package com.example.truckercore.layers.data_2.repository.impl

import com.example.truckercore.layers.data.base.mapper.impl.CompanyMapper
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data_2.remote.interfaces.CompanyRemoteDataSource
import com.example.truckercore.layers.data_2.repository.base.DataRepositoryBase
import com.example.truckercore.layers.data_2.repository.interfaces.CompanyRepository
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.model.company.Company
import kotlinx.coroutines.flow.Flow

class CompanyRepositoryImpl(
    override val remote: CompanyRemoteDataSource
) : DataRepositoryBase(remote), CompanyRepository {

    override suspend fun fetch(id: CompanyID): DataOutcome<Company> =
        fetch(
            data = remote.fetch(id),
            mapper = CompanyMapper::toEntity
        )

    override fun observe(id: CompanyID): Flow<DataOutcome<Company>> =
        observe(
            dataFlow = remote.observe(id),
            mapper = CompanyMapper::toEntity
        )


    override suspend fun save(company: Company): OperationOutcome =
        save(
            operation = { dto -> remote.save(dto) },
            mapper = { CompanyMapper.toDto(company) }
        )

}