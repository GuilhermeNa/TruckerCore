package com.example.truckercore.layers.data_2.repository

import com.example.truckercore.layers.data.base.mapper.impl.UserMapper
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data_2.contracts.DataRepository
import com.example.truckercore.layers.data_2.remote.UserRemoteDataSource
import com.example.truckercore.layers.data_2.repository.base.RepositoryPipeline
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.model.user.UserDraft
import kotlinx.coroutines.flow.Flow

class UserRepository(
    override val remote: UserRemoteDataSource,
    override val pipeline: RepositoryPipeline
): DataRepository {

    suspend fun fetch(uid: UID): DataOutcome<UserDraft> {
        return pipeline.fetch(
            search = { remote.fetch(uid) },
            toEntity = { UserMapper.toEntity(it) }
        )
    }

    fun observe(uid: UID): Flow<DataOutcome<UserDraft>> =
        pipeline.observe(
            observe = { remote.observe(uid) },
            toEntity = { UserMapper.toEntity(it) }
        )


}

