package com.example.truckercore.layers.data_2.repository.impl

import com.example.truckercore.layers.data.base.mapper.impl.AccessMapper
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data_2.remote.interfaces.AccessRemoteDataSource
import com.example.truckercore.layers.data_2.repository.base.DataRepositoryBase
import com.example.truckercore.layers.data_2.repository.interfaces.AccessRepository
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.Access
import kotlinx.coroutines.flow.Flow

class AccessRepositoryImpl(
    override val remote: AccessRemoteDataSource
) : DataRepositoryBase(remote),
    AccessRepository {

    override val classTag = "AccessRepositoryImpl"

    override fun observe(userId: UserID): Flow<DataOutcome<Access>> =
        observe(
            dataFlow = remote.observe(userId),
            mapper = { AccessMapper.toEntity(it) }
        )

}