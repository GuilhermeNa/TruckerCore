package com.example.truckercore.shared.modules.personal_data.errors

import com.example.truckercore.shared.errors.MappingException
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData

/**
 * Custom exception for errors related to mapping in [PersonalData].
 *
 * This exception can be thrown when mapping data between objects fails due to various reasons,
 * such as invalid data or configuration issues, making it easier to diagnose and handle mapping errors.
 *
 * @property message An optional message that provides additional details about the error.
 * @property cause The underlying exception that caused this exception to be thrown.
 */
class PersonalDataMappingException(message: String? = null, cause: Exception) :
    MappingException(message, cause)