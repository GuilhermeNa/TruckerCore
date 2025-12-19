package com.example.truckercore.layers.data.data_source.data

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.InfraException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.error.core.ErrorMapper
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException

/**
 * Maps low-level exceptions from the data source layer into application-specific exceptions.
 *
 * This implementation focuses on handling Firebase-related exceptions, translating them
 * into [AppException] instances that can be understood and handled in the domain layer.
 *
 * This ensures that all exceptions are consistently wrapped and no low-level errors leak
 * outside of the data source layer.
 */
class DataSourceErrorMapper : ErrorMapper {

    override fun invoke(e: Throwable): AppException = when (e) {
        is FirebaseNetworkException -> InfraException.Network(cause = e)
        is FirebaseFirestoreException -> DataException.DataSource(cause = e)
        else -> InfraException.Unknown(cause = e)
    }

}
