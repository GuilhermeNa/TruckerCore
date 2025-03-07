package com.example.truckercore.model.modules.fleet.trailer.enums

import com.example.truckercore.shared.errors.InvalidEnumParameterException

/**
 * Enum class representing different trailer brands within the system.
 * This enum defines the brands of trailers that can be used in the system.
 */
enum class TrailerBrand {
    RANDOM,
    LIBRELATO;

    companion object {

        fun convertString(nStr: String?): TrailerBrand {
            return nStr?.let { str ->
                if (enumExists(str)) valueOf(str)
                else throw InvalidEnumParameterException("Received an invalid string for TrailerBrand: $nStr.")
            }
                ?: throw NullPointerException("Received a null string and can not convert TrailerBrand.")
        }

        fun enumExists(str: String): Boolean =
            entries.any { it.name == str }

    }

}