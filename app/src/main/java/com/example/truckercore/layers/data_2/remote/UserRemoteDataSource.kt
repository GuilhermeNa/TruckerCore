package com.example.truckercore.layers.data_2.remote

import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.data_2.contracts.RemoteDataSource
import com.example.truckercore.layers.domain.base.ids.UID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class UserRemoteDataSource(private val firestore: FirebaseFirestore): RemoteDataSource {

    suspend fun fetch(uid: UID): UserDto? {

    }

    fun observe(uid: UID): Flow<UserDto?> {

    }

}