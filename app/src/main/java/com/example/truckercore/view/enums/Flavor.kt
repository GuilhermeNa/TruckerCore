package com.example.truckercore.view.enums

import java.security.InvalidParameterException

enum class Flavor(private val fieldName: String) {
    INDIVIDUAL("Trucker"),
    BUSINESS_ADMIN("Trucker Empresa"),
    BUSINESS_DRIVER("Trucker Motorista");

    fun getName() = fieldName

    companion object {

        fun fromFieldName(fieldName: String): Flavor {
            return entries.find { it.fieldName == fieldName }
                ?: throw InvalidParameterException(
                    "Received an invalid field name for flavors: $fieldName."
                )
        }

    }

}