package com.example.truckercore.modules.user.errors

import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.abstractions.ValidationException

/**
 * Custom exception for validation errors related to [User].
 *
 * The exception may be thrown when specific validation issues arise within the system,
 * providing a clear way to identify and handle these errors.
 *
 * @property message An optional message that describes the reason for the error.
 */
class UserValidationException(message: String? = null): ValidationException(message)
