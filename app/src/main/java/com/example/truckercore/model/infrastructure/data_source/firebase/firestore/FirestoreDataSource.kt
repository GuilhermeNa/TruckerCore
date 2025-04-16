package com.example.truckercore.model.infrastructure.data_source.firebase.firestore

import com.example.truckercore.model.shared.interfaces.Dto
import kotlinx.coroutines.flow.Flow

interface FirestoreDataSource {

    suspend fun <T : Dto>find(spec: Specification): T

    fun <T : Dto>flow(spec: Specification): Flow<T>

}