package com.example.truckercore.layers.data_2.remote.impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.data_2.remote.base.DataSource
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSourceBase
import com.example.truckercore.layers.data_2.remote.base.RemotePipeline
import com.example.truckercore.layers.data_2.remote.interfaces.UserRemoteDataSource
import com.example.truckercore.layers.domain.base.ids.UID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class UserRemoteDataSourceImpl(firestore: FirebaseFirestore, pipeline: RemotePipeline) :
    RemoteDataSourceBase<UserDto>(firestore, pipeline), UserRemoteDataSource {

    override val dtoClazz = UserDto::class.java

    override val collection = AppCollection.USER

    //----------------------------------------------------------------------------------------------

    override suspend fun fetch(uid: UID): UserDto? =
        pipeline.fetch(
            dataSource = dataSourceByUid(uid),
            dataClazz = dtoClazz
        )

    private fun dataSourceByUid(uid: UID): DataSource.Query {
        val query = firestore.collection(collection.name).whereEqualTo("uid", uid)
        return DataSource.Query(query)
    }

    override fun observe(uid: UID): Flow<UserDto?> =
        pipeline.observe(
            dataSource = dataSourceByUid(uid),
            dataClazz = dtoClazz
        )

}