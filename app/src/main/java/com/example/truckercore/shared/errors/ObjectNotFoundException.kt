package com.example.truckercore.shared.errors

/**
 * Custom exception thrown when an entity is not found.
 *
 * This exception is used to signal that the requested entity could not be found in the data source (e.g., database, API).
 * It extends the [Exception] class and allows for an optional custom message to be provided, which explains the specific
 * reason or context of the failure.
 *
 * @param message An optional message providing details about why the entity was not found. If no message is provided,
 *                the default value is `null`.
 *
 * @constructor Creates a new instance of [ObjectNotFoundException] with the optional message.
 */
class ObjectNotFoundException(message: String? = null) : Exception(message)
