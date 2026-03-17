package com.example.truckercore.layers.domain.use_case._base

import com.example.truckercore.core.error.DataException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.infra.logger.Logable
import com.example.truckercore.layers.data.base.outcome.DataOutcome

abstract class UseCase : Logable {

    abstract val classTag: String

    protected fun Throwable.toOutcome(): DataOutcome.Failure {
        val appException = when {
            this is AppException -> this
            else -> DataException.Unknown(
                message = "An unknown error occurred in UseCase.",
                cause = this
            )
        }
        return DataOutcome.Failure(appException)
    }

}