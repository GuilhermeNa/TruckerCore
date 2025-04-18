package com.example.truckercore.model.infrastructure.integration.exceptions

/**
 * Interface representing metadata about a specific type of application error.
 *
 * Implement this interface for each domain-specific group of error codes
 * (e.g., authentication errors, network errors, etc).
 *
 * This metadata allows consistent handling of errors in logs, UI, and error reporting tools.
 */
interface ErrorCode {

    /**
     * A technical name or identifier for this error.
     * Should be unique across the error domain and useful for logs and monitoring.
     */
    val name: String

    /**
     * A user-friendly message that can be shown directly to the user.
     * Should be localized and non-technical.
     */
    val userMessage: String

    /**
     * A detailed message intended for logging or developer use.
     * May contain technical information, stack trace references, or error context.
     */
    val logMessage: String

    /**
     * Indicates whether this error is recoverable.
     *
     * If true, the application should guide the user to retry, fix input, or take corrective action.
     * If false, the application may need to terminate the current flow, session, or even close entirely.
     */
    val isRecoverable: Boolean

}