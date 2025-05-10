package com.example.truckercore._test_data_provider

import com.example.truckercore.model.errors.AppExceptionOld
import com.example.truckercore.model.errors.ErrorCode

class TestAppExceptionProvider {

    data class TestErrorCode(
        override val name: String = "TEST_ERROR",
        override val userMessage: String = "Expected an user friendly message.",
        override val logMessage: String = "Expected a log message.",
        override val isRecoverable: Boolean = true
    ) : ErrorCode

    class TestAppException(
        message: String? = null,
        cause: Throwable? = null,
        errorCode: ErrorCode
    ) : AppExceptionOld(message, cause, errorCode)

    val cause = NullPointerException()

    val code = TestErrorCode()

    val appException = TestAppException(
        message = code.logMessage,
        cause = cause,
        errorCode = code
    )

}