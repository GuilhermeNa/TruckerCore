package com.example.truckercore.layers.data_2.remote.impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.impl.CompanyDto
import com.example.truckercore.layers.data_2.remote.base.DataSource
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSourceBase
import com.example.truckercore.layers.data_2.remote.base.RemotePipeline
import com.example.truckercore.layers.data_2.remote.interfaces.CompanyRemoteDataSource
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.google.firebase.firestore.FirebaseFirestore

class CompanyRemoteDataSourceImpl(firestore: FirebaseFirestore, pipeline: RemotePipeline) :
    RemoteDataSourceBase<CompanyDto>(firestore, pipeline), CompanyRemoteDataSource {

    override val dtoClazz = CompanyDto::class.java
    override val collection = AppCollection.COMPANY

    //----------------------------------------------------------------------------------------------

    override suspend fun fetch(companyId: CompanyID): CompanyDto? {
        return pipeline.fetch(
            dataSource = dataSourceById(companyId),
            dataClazz = dtoClazz
        )
    }

    private fun dataSourceById(companyId: CompanyID): DataSource.Document {
        val documentReference = firestore.document(companyId.value)
        return DataSource.Document(documentReference)
    }

}