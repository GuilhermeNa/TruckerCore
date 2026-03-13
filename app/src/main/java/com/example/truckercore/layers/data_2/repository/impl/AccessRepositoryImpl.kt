package com.example.truckercore.layers.data_2.repository.impl

import com.example.truckercore.layers.data.base.mapper.impl.AccessMapper
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data_2.repository.base.DataRepositoryBase
import com.example.truckercore.layers.data_2.repository.base.RepositoryPipeline
import com.example.truckercore.layers.data_2.remote.impl.AccessRemoteDataSourceImpl
import com.example.truckercore.layers.data_2.repository.interfaces.AccessRepository
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.access.Access
import kotlinx.coroutines.flow.Flow

class AccessRepositoryImpl(
    override val remote: AccessRemoteDataSourceImpl,
    pipeline: RepositoryPipeline
) : DataRepositoryBase(remote, pipeline), AccessRepository {

    override suspend fun fetch(userId: UserID): DataOutcome<Access> {
        return pipeline.fetch(
            data = remote.fetch(userId),
            toEntity = { AccessMapper.toEntity(it) }
        )
    }

    override fun observe(userId: UserID): Flow<DataOutcome<Access>> =
        pipeline.observe(
            dataFlow = remote.observe(userId) ,
            toEntity = { AccessMapper.toEntity(it) }
        )

}