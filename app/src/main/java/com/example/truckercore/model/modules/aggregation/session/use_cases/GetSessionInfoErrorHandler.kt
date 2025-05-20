package com.example.truckercore.model.modules.aggregation.session.use_cases

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.classes.AppResponseException
import com.example.truckercore.model.errors.domain.DomainException
import com.example.truckercore.model.errors.domain.error_code.RuleViolatedErrorCode
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.authentication.data.UID

object GetSessionInfoErrorHandler {

    private const val RESPONSE_ERROR_MSG =
        "The server returned an unexpected or invalid response while retrieving the session."
    private const val UNKNOWN_ERROR_MSG =
        "An unknown error occurred while trying to retrieve the session."

    operator fun invoke(e: Exception): DomainException {
        val (errorCode, message) = when (e) {
            is AppResponseException -> {
                Pair(RuleViolatedErrorCode.GetSession.UnexpectedResponse, RESPONSE_ERROR_MSG)
            }

            else ->
                Pair(RuleViolatedErrorCode.GetSession.Unknown, UNKNOWN_ERROR_MSG)
        }
        return DomainException.RuleViolated(
            code = errorCode,
            message = message,
            cause = e
        )
    }

    fun logError(message: String, uid: UID, response: AppResponse<Any>) {
        AppLogger.e(
            "${GetSessionInfoUseCaseImpl::class.simpleName}",
            "$message Uid: $uid. Response: $response."
        )
    }

}