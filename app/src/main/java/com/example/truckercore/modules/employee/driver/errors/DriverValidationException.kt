package com.example.truckercore.modules.employee.driver.errors

import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.shared.errors.abstractions.ValidationException

/**
 * Custom exception for validation errors related to [Driver].
 *
 * The exception may be thrown when specific validation issues arise within the system,
 * providing a clear way to identify and handle these errors.
 *
 * @property message An optional message that describes the reason for the error.
 */
class DriverValidationException(message: String? = null) : ValidationException(message)