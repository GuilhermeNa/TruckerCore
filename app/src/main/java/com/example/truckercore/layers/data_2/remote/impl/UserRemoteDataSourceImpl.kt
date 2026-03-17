package com.example.truckercore.layers.data_2.remote.impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSourceBase
import com.example.truckercore.layers.data_2.remote.interfaces.UserRemoteDataSource
import com.example.truckercore.layers.domain.base.ids.UID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class UserRemoteDataSourceImpl(firestore: FirebaseFirestore) :
    RemoteDataSourceBase<UserDto>(firestore),
    UserRemoteDataSource {

    override val dtoClazz = UserDto::class.java
    override val collection = AppCollection.USER

    override fun observe(uid: UID): Flow<UserDto?> =
        observe(source = getQuery(uid), dataClazz = dtoClazz)

}