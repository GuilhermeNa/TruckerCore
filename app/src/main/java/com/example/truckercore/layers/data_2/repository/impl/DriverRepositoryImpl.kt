package com.example.truckercore.layers.data_2.repository.impl

import com.example.truckercore.layers.data.base.mapper.impl.DriverMapper
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data_2.remote.interfaces.DriverRemoteDataSource
import com.example.truckercore.layers.data_2.repository.base.DataRepositoryBase
import com.example.truckercore.layers.data_2.repository.interfaces.DriverRepository
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.driver.Driver
import kotlinx.coroutines.flow.Flow

class DriverRepositoryImpl(
    override val remote: DriverRemoteDataSource
) : DataRepositoryBase(remote),
    DriverRepository {

    override fun observe(userId: UserID): Flow<DataOutcome<Driver>> =
        observe(
            dataFlow = remote.observe(userId),
            mapper = { DriverMapper.toEntity(it) }
        )

}