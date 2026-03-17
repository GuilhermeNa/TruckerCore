package com.example.truckercore.layers.data_2.remote.impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.impl.AdminDto
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSourceBase
import com.example.truckercore.layers.data_2.remote.interfaces.AdminRemoteDataSource
import com.example.truckercore.layers.domain.base.ids.UserID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class AdminRemoteDataSourceImpl(firestore: FirebaseFirestore) :
    RemoteDataSourceBase<AdminDto>(firestore),
    AdminRemoteDataSource {

    override val dtoClazz = AdminDto::class.java
    override val collection = AppCollection.ADMIN

    override fun observe(userId: UserID): Flow<AdminDto?> =
        observe(source = getQuery(userId), dataClazz = dtoClazz)

}

