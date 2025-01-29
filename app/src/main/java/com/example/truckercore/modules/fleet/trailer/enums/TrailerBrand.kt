package com.example.truckercore.modules.fleet.trailer.enums

import com.example.truckercore.shared.errors.InvalidEnumParameterException

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