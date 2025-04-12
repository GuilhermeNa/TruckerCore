package com.example.truckercore.model.infrastructure.security.authentication.errors

import com.example.truckercore.model.shared.errors._main.ErrorCode

/**
 * Interface that represents error codes specific to authentication processes.
 *
 * This interface extends [ErrorCode] and is used to categorize error codes that are related to authentication module.
 * Each implementation of this interface will define the error's name, log message, user message, and whether the error is recoverable.
 */
interface AuthErrorCode: ErrorCode