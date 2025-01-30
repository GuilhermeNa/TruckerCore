package com.example.truckercore.modules.fleet.shared.module.licensing.errors

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.shared.errors.abstractions.ValidationException

/**
 * Custom exception for validation errors related to [Licensing].
 *
 * The exception may be thrown when specific validation issues arise within the system,
 * providing a clear way to identify and handle these errors.
 *
 * @property message An optional message that describes the reason for the error.
 */
class LicensingValidationException(message: String? = null) : ValidationException(message)