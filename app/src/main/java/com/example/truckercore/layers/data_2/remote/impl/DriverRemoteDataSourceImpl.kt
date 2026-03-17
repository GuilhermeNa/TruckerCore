package com.example.truckercore.layers.data_2.remote.impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.impl.DriverDto
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSourceBase
import com.example.truckercore.layers.data_2.remote.interfaces.DriverRemoteDataSource
import com.example.truckercore.layers.domain.base.ids.UserID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class DriverRemoteDataSourceImpl(firestore: FirebaseFirestore) :
    RemoteDataSourceBase<DriverDto>(firestore),
    DriverRemoteDataSource {

    override val dtoClazz = DriverDto::class.java
    override val collection = AppCollection.DRIVER

    override fun observe(userId: UserID): Flow<DriverDto?> =
        observe(source = getQuery(userId), dataClazz = dtoClazz)

}