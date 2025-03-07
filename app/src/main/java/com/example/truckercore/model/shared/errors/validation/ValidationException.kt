package com.example.truckercore.model.shared.errors.validation

/**
 * Custom exception class used for validation errors.
 *
 * This exception is thrown when a validation check fails. It extends the [Exception] class
 * and allows the option of providing a custom error message to describe the specific validation error.
 */
abstract class ValidationException : Exception()