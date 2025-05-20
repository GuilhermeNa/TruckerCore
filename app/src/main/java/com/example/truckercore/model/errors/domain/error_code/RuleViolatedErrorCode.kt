package com.example.truckercore.model.errors.domain.error_code

import com.example.truckercore.model.errors.domain.contracts.DomainErrorCode

sealed class RuleViolatedErrorCode: DomainErrorCode {

    sealed class GetSession: RuleViolatedErrorCode() {
        data object UnexpectedResponse: GetSession()
        data object Unknown: GetSession()
    }

}