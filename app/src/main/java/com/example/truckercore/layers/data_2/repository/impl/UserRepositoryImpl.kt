package com.example.truckercore.layers.data_2.repository.impl

import com.example.truckercore.layers.data.base.mapper.impl.UserMapper
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data_2.remote.interfaces.UserRemoteDataSource
import com.example.truckercore.layers.data_2.repository.base.DataRepositoryBase
import com.example.truckercore.layers.data_2.repository.interfaces.UserRepository
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.model.user.UserDraft
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    override val remote: UserRemoteDataSource
) : DataRepositoryBase(remote),
    UserRepository {

    override val classTag = "UserRepositoryImpl"

    override fun observe(uid: UID): Flow<DataOutcome<UserDraft>> =
        observe(
            dataFlow = remote.observe(uid),
            mapper = { UserMapper.toEntity(it) }
        )

}


