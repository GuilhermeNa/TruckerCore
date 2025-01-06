package com.example.truckercore.shared.utils.expressions

import com.example.truckercore.shared.annotations.Required
import com.example.truckercore.shared.interfaces.Dto
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

fun Dto<*>.getMissingFields(): List<String> {
    val missingFields = mutableListOf<String>()

    this::class.memberProperties.forEach { property ->
        if (property.annotations.any { it is Required }) {
            val value = (property as KProperty1<Dto<*>, Any?>).get(this)

            if (value == null) {
                missingFields.add(property.name)
            } else if (value is String && (value.isBlank() || value.isEmpty())) {
                missingFields.add(property.name)
            }

        }
    }

    return missingFields
}