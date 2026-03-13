package com.example.truckercore.layers.data_2.remote.impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.impl.AccessDto
import com.example.truckercore.layers.data_2.remote.base.DataSource
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSourceBase
import com.example.truckercore.layers.data_2.remote.base.RemotePipeline
import com.example.truckercore.layers.data_2.remote.interfaces.AccessRemoteDataSource
import com.example.truckercore.layers.domain.base.ids.UserID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class AccessRemoteDataSourceImpl(firestore: FirebaseFirestore, pipeline: RemotePipeline) :
    RemoteDataSourceBase<AccessDto>(firestore, pipeline), AccessRemoteDataSource {

    override val dtoClazz = AccessDto::class.java

    override val collection = AppCollection.ACCESS

    //----------------------------------------------------------------------------------------------

    override suspend fun fetch(userId: UserID): AccessDto? {
        return pipeline.fetch(
            dataSource = dataSourceByUserId(userId),
            dataClazz = dtoClazz
        )
    }

    private fun dataSourceByUserId(userId: UserID): DataSource.Query {
        val query = firestore.collection(collection.name).whereEqualTo("userId", userId)
        return DataSource.Query(query)
    }

    override fun observe(userId: UserID): Flow<AccessDto?> =
        pipeline.observe(
            dataSource = dataSourceByUserId(userId),
            dataClazz = dtoClazz
        )

}