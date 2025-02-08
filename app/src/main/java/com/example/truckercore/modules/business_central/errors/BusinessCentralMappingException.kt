package com.example.truckercore.modules.business_central.errors

import com.example.truckercore.shared.errors.abstractions.MappingException

/**
 * Custom exception for errors related to mapping in BusinessCentral.
 *
 * This exception can be thrown when mapping data between objects fails due to various reasons,
 * such as invalid data or configuration issues, making it easier to diagnose and handle mapping errors.
 *
 * @property message An optional message that provides additional details about the error.
 * @property cause The underlying exception that caused this exception to be thrown.
 */
class BusinessCentralMappingException(message: String? = null, cause: Exception) :
    MappingException(message, cause)