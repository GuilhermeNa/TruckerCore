package com.example.truckercore.model.modules.user.enums

import com.example.truckercore.shared.errors.InvalidEnumParameterException

/**
 * Enum class representing different categories of persons in the system.
 * It defines two categories: ADMIN and DRIVER. These categories help distinguish between different
 * roles or types of users in the system.
 *
 * @property ADMIN Represents an administrative user in the system who has higher privileges.
 * @property DRIVER Represents a user who is a driver and may have restricted privileges.
 */
enum class PersonCategory {
    ADMIN,
    DRIVER;

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