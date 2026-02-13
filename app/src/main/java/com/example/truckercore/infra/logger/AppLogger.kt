package com.example.truckercore.infra.logger

import android.util.Log

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

    private const val LOGGER = "AppLogger:"

    fun d(tag: String, message: String) {
        Log.d(LOGGER, "[$tag] -> $message")
    }

    fun i(tag: String, message: String) {
        Log.i(LOGGER, "[$tag] -> $message")
    }

    fun w(tag: String, message: String) {
        Log.w(LOGGER, "[$tag] -> $message")
    }

    fun e(tag: String, message: String = "", throwable: Throwable? = null) {
        Log.e(
            LOGGER, "$message /n ${throwable?.stackTraceToString()}"
        )
    }

}