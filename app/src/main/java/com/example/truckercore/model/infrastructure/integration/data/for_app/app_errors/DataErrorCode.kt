package com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors

import com.example.truckercore.model.errors.ErrorCode

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