package com.example.truckercore.layers.data_2.repository.impl

import com.example.truckercore.layers.data.base.mapper.impl.UserMapper
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data_2.remote.impl.UserRemoteDataSourceImpl
import com.example.truckercore.layers.data_2.repository.base.DataRepositoryBase
import com.example.truckercore.layers.data_2.repository.base.RepositoryPipeline
import com.example.truckercore.layers.data_2.repository.interfaces.UserRepository
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.model.user.UserDraft
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    override val remote: UserRemoteDataSourceImpl,
    pipeline: RepositoryPipeline
) : DataRepositoryBase(remote, pipeline), UserRepository {

    override suspend fun fetch(uid: UID): DataOutcome<UserDraft> {
        return pipeline.fetch(
            data = remote.fetch(uid),
            toEntity = { UserMapper.toEntity(it) }
        )
    }

    override fun observe(uid: UID): Flow<DataOutcome<UserDraft>> =
        pipeline.observe(
            dataFlow = remote.observe(uid),
            toEntity = { UserMapper.toEntity(it) }
        )

}


