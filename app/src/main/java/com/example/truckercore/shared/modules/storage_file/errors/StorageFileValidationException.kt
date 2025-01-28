package com.example.truckercore.shared.modules.storage_file.errors

import com.example.truckercore.shared.errors.abstractions.ValidationException
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile

/**
 * Custom exception for validation errors related to [StorageFile].
 *
 * The exception may be thrown when specific validation issues arise within the system,
 * providing a clear way to identify and handle these errors.
 *
 * @property message An optional message that describes the reason for the error.
 */
class StorageFileValidationException(message: String? = null): ValidationException(message)