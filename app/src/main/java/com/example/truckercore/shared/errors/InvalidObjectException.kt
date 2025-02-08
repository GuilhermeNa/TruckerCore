package com.example.truckercore.shared.errors

import com.example.truckercore.shared.errors.abstractions.ValidationException
import kotlin.reflect.KClass

class InvalidObjectException(obj: KClass<*>, invalidFields: List<String>):ValidationException() {
}