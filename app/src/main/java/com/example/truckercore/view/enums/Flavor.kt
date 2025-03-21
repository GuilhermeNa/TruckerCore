package com.example.truckercore.view.enums

import com.example.truckercore.model.configs.app_constants.Parent
import com.example.truckercore.model.shared.errors.InvalidEnumParameterException

enum class Flavor(private val fieldName: String) {
    INDIVIDUAL("Trucker"),
    BUSINESS_ADMIN("Trucker Empresa"),
    BUSINESS_DRIVER("Trucker Motorista");

    fun getName() = fieldName

    companion object {

        fun fromFieldName(fieldName: String): Flavor? {
            return entries.find { it.fieldName == fieldName }
        }

    }

}