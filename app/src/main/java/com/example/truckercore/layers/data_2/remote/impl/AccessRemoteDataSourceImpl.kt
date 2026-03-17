package com.example.truckercore.layers.data_2.remote.impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.impl.AccessDto
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSourceBase
import com.example.truckercore.layers.data_2.remote.interfaces.AccessRemoteDataSource
import com.example.truckercore.layers.domain.base.ids.UserID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class AccessRemoteDataSourceImpl(firestore: FirebaseFirestore) :
    RemoteDataSourceBase<AccessDto>(firestore),
    AccessRemoteDataSource {

    override val dtoClazz = AccessDto::class.java
    override val collection = AppCollection.ACCESS

    override fun observe(userId: UserID): Flow<AccessDto?> =
        observe(source = getQuery(userId), dataClazz = dtoClazz)

}