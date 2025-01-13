package com.example.truckercore.shared.errors

/**
 * Custom exception class used for validation errors.
 *
 * This exception is thrown when a validation check fails. It extends the [Exception] class
 * and allows the option of providing a custom error message to describe the specific validation error.
 *
 * @property message An optional message that provides more details about the validation error.
 */
abstract class ValidationException(message: String? = null): Exception(message)