package com.example.truckercore.layers.data_2.remote.base

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.google.firebase.firestore.FirebaseFirestore

abstract class RemoteDataSourceBase<D: BaseDto>(
    protected val firestore: FirebaseFirestore,
    protected val pipeline: RemotePipeline
) {

    abstract val dtoClazz: Class<D>

    abstract val collection: AppCollection

}