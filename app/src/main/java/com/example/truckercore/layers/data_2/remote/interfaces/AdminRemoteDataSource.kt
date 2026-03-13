package com.example.truckercore.layers.data_2.remote.interfaces

import com.example.truckercore.layers.data.base.dto.impl.AdminDto
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSource
import com.example.truckercore.layers.domain.base.ids.UserID
import kotlinx.coroutines.flow.Flow

interface AdminRemoteDataSource : RemoteDataSource {

    suspend fun fetch(userId: UserID): AdminDto?

    fun observe(userId: UserID): Flow<AdminDto?>

}