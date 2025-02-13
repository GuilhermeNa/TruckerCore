package com.example.truckercore.shared.errors.mapping

import com.example.truckercore.shared.errors.abstractions.MappingException
import kotlin.reflect.KClass

class IllegalMappingArgumentException(
    val expected: KClass<*>,
    val received: KClass<*>
) : MappingException() {

    fun message(): String = "Expected a ${expected::class.simpleName} " +
            "for mapping but received: ${received::class.simpleName}."

}