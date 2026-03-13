package com.example.truckercore.layers.data_2.repository.base

import com.example.truckercore.layers.data_2.remote.base.RemoteDataSource

abstract class DataRepositoryBase(
    protected open val remote: RemoteDataSource,
    protected open val pipeline: RepositoryPipeline
)