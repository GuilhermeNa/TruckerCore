package com.example.truckercore.model.configs.app_constants

import com.example.truckercore.model.shared.errors.InvalidEnumParameterException

enum class Parent {
    TRUCK,
    TRAILER,
    DRIVER,
    ADMIN;

    companion object {

        fun convertString(nStr: String?): Parent {
            return nStr?.let { str ->
                if (enumExists(str)) Parent.valueOf(str)
                else throw InvalidEnumParameterException("Received an invalid string for Parent: $nStr.")
            }
                ?: throw NullPointerException("Received a null string and can not convert Parent.")
        }

        fun enumExists(str: String): Boolean =
            Parent.entries.any { it.name == str }

    }

}