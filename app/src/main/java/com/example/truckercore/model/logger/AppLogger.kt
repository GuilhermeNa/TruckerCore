package com.example.truckercore.model.logger

import android.util.Log
import com.example.truckercore._utils.expressions.getClassName

/**
 * AppLogger is a centralized logging utility for the application.
 *
 * It provides structured and environment-aware logging for various levels,
 * ensuring that debug and informational messages are only logged in debug builds,
 * while warnings and errors are always logged regardless of the build type.
 *
 * Usage:
 * - Use `d()` for detailed debug messages.
 * - Use `i()` for general informational events.
 * - Use `w()` for unusual but non-breaking behavior.
 * - Use `e()` for exceptions and error messages.
 * - Use `wtf()` for critical issues that should never happen.
 *
 * Example:
 * ```kotlin
 * AppLogger.d("LoginViewModel", "User data loaded")
 * AppLogger.e("UserService", "Failed to fetch user", exception)
 * ```
 *
 * Notes:
 * - Debug and info logs are disabled in release builds to avoid leaking internal information.
 * - Error and warning logs are retained to help diagnose issues in production.
 * - This class can be extended to integrate with crash reporting tools like Firebase Crashlytics.
 */
object AppLogger {

    private const val M_LOG = "mLog:"
    private const val DEFAULT_TAG = "AppLogger"

    /**
     * Logs a debug-level message.
     * Used for development-time diagnostics, such as variable values or execution flow.
     * These logs are only shown in debug builds.
     *
     * @param tag A custom tag identifying the source of the log message.
     * @param message The debug message to log.
     */
    fun d(tag: String = DEFAULT_TAG, message: String) {
        // if (BuildConfig.DEBUG)
        Log.d("$M_LOG [$tag]", message)
    }

    /**
     * Logs an informational message.
     * Represents general application events such as successful operations.
     * Only shown in debug builds.
     *
     * @param tag A custom tag identifying the source of the log message.
     * @param message The informational message to log.
     */
    fun i(tag: String = DEFAULT_TAG, message: String) {
        //if (BuildConfig.DEBUG)
        Log.i("$M_LOG [$tag]", message)
    }

    /**
     * Logs a warning message.
     * Represents unexpected or borderline-invalid states that do not crash the app.
     * Logged in debug builds.
     *
     * @param tag A custom tag identifying the source of the log message.
     * @param message The warning message to log.
     */
    fun w(tag: String = DEFAULT_TAG, message: String) {
        // if (BuildConfig.DEBUG)
        Log.w("$M_LOG [$tag]", message)
    }

    /**
     * Logs an error message along with an optional exception.
     * Always logged regardless of build type.
     *
     * @param tag A custom tag identifying the source of the log message.
     * @param message The error message to log.
     * @param throwable An optional Throwable to include with the error log.
     */
    fun e(tag: String = DEFAULT_TAG, message: String, throwable: Throwable? = null) {
        val logMessage = throwable?.let { t ->
            "$message ${t.getClassName()} - ${t.message}"
        } ?: message

        Log.e("$M_LOG [$tag]", logMessage)
    }

    /**
     * Logs a critical failure that should never happen under normal conditions.
     * Use with caution. Logged in both debug and release builds.
     *
     * @param tag A custom tag identifying the source of the log message.
     * @param message The critical failure message to log.
     * @param throwable An optional Throwable to include with the log.
     */
    fun wtf(tag: String = DEFAULT_TAG, message: String, throwable: Throwable? = null) {
        Log.wtf("$M_LOG [$tag]", message, throwable)
    }

}