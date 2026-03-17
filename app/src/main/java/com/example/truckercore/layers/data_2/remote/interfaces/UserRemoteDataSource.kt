package com.example.truckercore.layers.data_2.remote.interfaces

import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSource
import com.example.truckercore.layers.domain.base.ids.UID
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource : RemoteDataSource {

    fun observe(uid: UID): Flow<UserDto?>

}