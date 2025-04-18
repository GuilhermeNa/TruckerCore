package com.example.truckercore.model.infrastructure.security.authentication.app_errors

import com.example.truckercore.model.infrastructure.integration.exceptions.ErrorCode

/**
 * Interface that represents error codes specific to authentication processes.
 *
 * This interface extends [ErrorCode] and is used to categorize error codes that are related to authentication module.
 * Each implementation of this interface will define the error's name, log message, user message, and whether the error is recoverable.
 */
interface AuthErrorCode: ErrorCode