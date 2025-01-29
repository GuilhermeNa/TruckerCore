package com.example.truckercore.modules.fleet.trailer.enums

import com.example.truckercore.shared.errors.InvalidEnumParameterException

enum class TrailerCategory {
    THREE_AXIS,
    FOUR_AXIS,
    ROAD_TRAIN_FRONT,
    ROAD_TRAIN_REAR,
    DOLLY,
    BI_TRUCK_FRONT,
    BI_TRUCK_REAR;

    companion object {

        fun convertString(nStr: String?): TrailerCategory {
            return nStr?.let { str ->
                if (enumExists(str)) valueOf(str)
                else throw InvalidEnumParameterException("Received an invalid string for TrailerCategory: $nStr.")
            }
                ?: throw NullPointerException("Received a null string and can not convert TrailerCategory.")
        }

        fun enumExists(str: String): Boolean =
            entries.any { it.name == str }

    }

}