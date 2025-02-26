package com.example.truckercore.shared.errors.mapping

import kotlin.reflect.KClass

/**
 * Custom exception class used to handle errors related to invalid arguments passed during the mapping process.
 * This exception is thrown when there is a mismatch between the expected and received classes for a mapping operation.
 *
 * @property expected The class type that was expected during the mapping process.
 * @property received The class type that was actually received during the mapping process.
 *
 */
class IllegalMappingArgumentException(
    val expected: KClass<*>,
    val received: KClass<*>
) : MappingException() {

    /**
     * Generates an error message that describes the mismatch between the expected and received class types.
     * This message helps in understanding the mapping issue, specifically which types were expected and received.
     *
     * @return A message describing the mismatch between the expected and received classes for mapping.
     */
    fun message(): String = "Expected a ${expected::class.simpleName} " +
            "for mapping but received: ${received::class.simpleName}."

}