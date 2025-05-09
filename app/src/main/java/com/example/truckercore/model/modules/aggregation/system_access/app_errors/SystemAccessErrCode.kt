package com.example.truckercore.model.modules.aggregation.system_access.app_errors

import com.example.truckercore.model.errors.ErrorCode

/**
 * Marker interface for representing specific error codes related to the system access domain.
 *
 * This interface extends [ErrorCode], allowing system access errors to integrate with the application's
 * centralized error-handling framework.
 *
 */
interface SystemAccessErrCode: ErrorCode