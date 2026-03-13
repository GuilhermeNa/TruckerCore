package com.example.truckercore.layers.data_2.remote.impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.impl.DriverDto
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSourceBase
import com.example.truckercore.layers.data_2.remote.base.RemotePipeline
import com.example.truckercore.layers.data_2.remote.interfaces.DriverRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore

class DriverRemoteDataSourceImpl(firestore: FirebaseFirestore, pipeline: RemotePipeline) :
    RemoteDataSourceBase<DriverDto>(firestore, pipeline), DriverRemoteDataSource {

    override val dtoClazz = DriverDto::class.java
    override val collection = AppCollection.DRIVER


}