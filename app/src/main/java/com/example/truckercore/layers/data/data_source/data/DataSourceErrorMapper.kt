package com.example.truckercore.layers.data.data_source.data

import com.example.truckercore.core.error.InfraException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.error.core.ErrorMapper
import com.google.firebase.FirebaseNetworkException

class DataSourceErrorMapper : ErrorMapper {

    override fun invoke(e: Throwable): AppException =
        when (e) {
            is FirebaseNetworkException -> InfraException.Network(cause = e)
            else -> InfraException.Unknown(cause = e)
        }

}
