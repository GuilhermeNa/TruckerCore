package com.example.truckercore.view.ui_error

import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.domain.DomainException
import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.errors.technical.TechnicalException

object UiErrorFactory {

    operator fun invoke(e: AppException): UiError {
       TODO()
        /* return when (e) {
            is InfraException -> UiErrorFactoryInfraExceptionHandler(e)
            is DomainException -> UiErrorFactoryDomainExceptionHandler(e)
            is TechnicalException -> UiErrorFactoryTechnicalExceptionHandler(e)
            else -> TODO()
        }*/
    }

}