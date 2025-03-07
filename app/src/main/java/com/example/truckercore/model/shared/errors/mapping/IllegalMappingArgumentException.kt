package com.example.truckercore.model.shared.errors.mapping

import kotlin.reflect.KProperty0

/**
 * Custom exception class used to handle errors related to invalid arguments passed during the mapping process.
 * This exception is thrown when there is a mismatch between the expected and received classes for a mapping operation.
 *
 * @property expected The class type that was expected during the mapping process.
 * @property received The class type that was actually received during the mapping process.
 *
 */
class IllegalMappingArgumentException(
    val expected: String?,
    val received: String?
) : MappingException() {

    override val message = "Expected a $expected for mapping but received: $received."

}