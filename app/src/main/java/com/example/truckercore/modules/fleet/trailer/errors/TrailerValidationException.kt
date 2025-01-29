package com.example.truckercore.modules.fleet.trailer.errors

import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.shared.errors.abstractions.ValidationException

/**
 * Custom exception for validation errors related to [Trailer].
 *
 * The exception may be thrown when specific validation issues arise within the system,
 * providing a clear way to identify and handle these errors.
 *
 * @property message An optional message that describes the reason for the error.
 */
class TrailerValidationException(message: String? = null) : ValidationException(message)