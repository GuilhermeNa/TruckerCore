package com.example.truckercore.layers.data_2.remote.impl

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.dto.impl.CompanyDto
import com.example.truckercore.layers.data_2.remote.base.RemoteDataSourceBase
import com.example.truckercore.layers.data_2.remote.interfaces.CompanyRemoteDataSource
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.google.firebase.firestore.FirebaseFirestore

class CompanyRemoteDataSourceImpl(firestore: FirebaseFirestore) :
    RemoteDataSourceBase<CompanyDto>(firestore),
    CompanyRemoteDataSource {

    override val dtoClazz = CompanyDto::class.java
    override val collection = AppCollection.COMPANY

    override suspend fun fetch(id: CompanyID): CompanyDto? =
        fetch(source = getDocument(id), dataClazz = dtoClazz)


}