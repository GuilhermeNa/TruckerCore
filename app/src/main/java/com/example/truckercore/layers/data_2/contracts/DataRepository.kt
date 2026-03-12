package com.example.truckercore.layers.data_2.contracts

import com.example.truckercore.layers.data_2.repository.base.RepositoryPipeline

interface DataRepository {

    val pipeline: RepositoryPipeline

    val remote: RemoteDataSource

}