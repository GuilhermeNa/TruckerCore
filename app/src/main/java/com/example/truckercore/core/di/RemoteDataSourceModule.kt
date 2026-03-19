package com.example.truckercore.core.di

import com.example.truckercore.layers.data_2.remote.impl.AccessRemoteDataSourceImpl
import com.example.truckercore.layers.data_2.remote.impl.AdminRemoteDataSourceImpl
import com.example.truckercore.layers.data_2.remote.impl.CompanyRemoteDataSourceImpl
import com.example.truckercore.layers.data_2.remote.impl.DriverRemoteDataSourceImpl
import com.example.truckercore.layers.data_2.remote.impl.UserRemoteDataSourceImpl
import com.example.truckercore.layers.data_2.remote.interfaces.AccessRemoteDataSource
import com.example.truckercore.layers.data_2.remote.interfaces.AdminRemoteDataSource
import com.example.truckercore.layers.data_2.remote.interfaces.CompanyRemoteDataSource
import com.example.truckercore.layers.data_2.remote.interfaces.DriverRemoteDataSource
import com.example.truckercore.layers.data_2.remote.interfaces.UserRemoteDataSource
import org.koin.dsl.module

val remoteSourceModule = module {
    single<AccessRemoteDataSource> { AccessRemoteDataSourceImpl(get()) }
    single<AdminRemoteDataSource> { AdminRemoteDataSourceImpl(get()) }
    single<DriverRemoteDataSource> { DriverRemoteDataSourceImpl(get()) }
    single<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
    single<CompanyRemoteDataSource> { CompanyRemoteDataSourceImpl(get()) }
}