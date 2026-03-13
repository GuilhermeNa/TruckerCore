package com.example.truckercore.layers.data_2.remote.impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.impl.AdminDto
import com.example.truckercore.layers.data_2.remote.base.DataSource
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSourceBase
import com.example.truckercore.layers.data_2.remote.base.RemotePipeline
import com.example.truckercore.layers.data_2.remote.interfaces.AdminRemoteDataSource
import com.example.truckercore.layers.domain.base.ids.UserID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class AdminRemoteDataSourceImpl(firestore: FirebaseFirestore, pipeline: RemotePipeline) :
    RemoteDataSourceBase<AdminDto>(firestore, pipeline), AdminRemoteDataSource {

    override val dtoClazz = AdminDto::class.java

    override val collection = AppCollection.ADMIN

    //----------------------------------------------------------------------------------------------

    override suspend fun fetch(userId: UserID): AdminDto? {
        return pipeline.fetch(
            dataSource = dataSourceByUserId(userId),
            dataClazz = dtoClazz
        )
    }

    private fun dataSourceByUserId(userId: UserID): DataSource.Query {
        val query = firestore.collection(collection.name).whereEqualTo("userId", userId)
        return DataSource.Query(query)
    }

    override fun observe(userId: UserID): Flow<AdminDto?> =
        pipeline.observe(
            dataSource = dataSourceByUserId(userId),
            dataClazz = dtoClazz
        )

}

