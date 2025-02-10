package com.example.truckercore.modules.user.enums

import com.example.truckercore.shared.errors.InvalidEnumParameterException

enum class PersonCategory {
    ADMIN, DRIVER;

    companion object {
        fun convertString(nStr: String?): PersonCategory {
            return nStr?.let { str ->
                if (enumExists(str)) PersonCategory.valueOf(str)
                else throw InvalidEnumParameterException("Received an invalid string for PersonCategory: $nStr.")
            } ?: throw NullPointerException("Received a null string and can not convert PersonCategory.")
        }

        fun enumExists(str: String): Boolean =
            PersonCategory.entries.any { it.name == str }
    }

}