package com.example.truckercore.shared.modules.personal_data.errors

import com.example.truckercore.shared.errors.abstractions.ValidationException
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData

/**
 * Custom exception for validation errors related to [PersonalData].
 *
 * The exception may be thrown when specific validation issues arise within the system,
 * providing a clear way to identify and handle these errors.
 *
 * @property message An optional message that describes the reason for the error.
 */
class PersonalDataValidationException(message: String? = null) : ValidationException(message)