package com.example.truckercore.modules.fleet.truck.enums

import com.example.truckercore.shared.errors.InvalidEnumParameterException

enum class TruckBrand {
    SCANIA, VOLVO;

    companion object {

        fun convertString(nStr: String?): TruckBrand {
            return nStr?.let { str ->
                if (enumExists(str)) valueOf(str)
                else throw InvalidEnumParameterException("Received an invalid string for TruckBrand: $nStr.")
            }
                ?: throw NullPointerException("Received a null string and can not convert TruckBrand.")
        }

        fun enumExists(str: String): Boolean =
            entries.any { it.name == str }

    }

}