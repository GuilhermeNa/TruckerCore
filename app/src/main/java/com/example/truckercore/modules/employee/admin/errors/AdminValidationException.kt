package com.example.truckercore.modules.employee.admin.errors

import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.shared.errors.abstractions.ValidationException

/**
 * Custom exception for validation errors related to [Admin].
 *
 * The exception may be thrown when specific validation issues arise within the system,
 * providing a clear way to identify and handle these errors.
 *
 * @property message An optional message that describes the reason for the error.
 */
class AdminValidationException(message: String? = null) : ValidationException(message)