package com.example.truckercore.layers.data_2.repository.impl

import com.example.truckercore.layers.data.base.mapper.impl.AdminMapper
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data_2.remote.interfaces.AdminRemoteDataSource
import com.example.truckercore.layers.data_2.repository.base.DataRepositoryBase
import com.example.truckercore.layers.data_2.repository.interfaces.AdminRepository
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.admin.Admin
import kotlinx.coroutines.flow.Flow

class AdminRepositoryImpl(
    override val remote: AdminRemoteDataSource
) : DataRepositoryBase(remote),
    AdminRepository {

    override fun observe(userId: UserID): Flow<DataOutcome<Admin>> =
        observe(
            dataFlow = remote.observe(userId),
            mapper = { AdminMapper.toEntity(it) }
        )

}