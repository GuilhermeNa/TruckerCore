package com.example.truckercore.model.modules.fleet.trailer.enums

import com.example.truckercore.shared.errors.InvalidEnumParameterException

/**
 * Enum class representing different trailer categories within the system.
 *
 * This enum defines various categories of trailers used in the system, each representing
 * different configurations and uses of trailers. These categories are essential for managing
 * and classifying trailers in the fleet.
 */
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