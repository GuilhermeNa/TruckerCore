package com.example.truckercore.view.ui_error

import com.example.truckercore.model.errors.exceptions.AppException
import com.example.truckercore.model.errors.exceptions.domain.DomainException
import com.example.truckercore.model.errors.exceptions.infra.InfraException
import com.example.truckercore.model.errors.exceptions.technical.TechnicalException

object UiErrorFactory {

    operator fun invoke(e: AppException): UiError {
        return when (e) {
            is InfraException -> UiErrorFactoryInfraExceptionHandler(e)
            is DomainException -> UiErrorFactoryDomainExceptionHandler(e)
            is TechnicalException -> UiErrorFactoryTechnicalExceptionHandler(e)
            else -> TODO()
        }
    }

}