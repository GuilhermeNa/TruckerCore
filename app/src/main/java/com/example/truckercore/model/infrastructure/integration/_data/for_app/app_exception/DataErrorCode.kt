package com.example.truckercore.model.infrastructure.integration._data.for_app.app_exception

import com.example.truckercore.model.infrastructure.app_exception.ErrorCode

/**
 * Marker interface representing error codes specific to data layer operations.
 *
 * This interface extends the base [ErrorCode], allowing [DataAppException] to encapsulate
 * structured, domain-specific error types.
 *
 * Implementations of this interface are used to:
 * - Categorize data-related failures (e.g., interpretation errors, mapping issues, network problems)
 * - Provide consistent error handling across repositories and services
 * - Support user-friendly error messages and developer-focused logging
 */
interface DataErrorCode: ErrorCode