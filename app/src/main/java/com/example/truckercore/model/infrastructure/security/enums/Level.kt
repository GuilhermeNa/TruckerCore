package com.example.truckercore.model.infrastructure.security.enums

import com.example.truckercore.model.shared.errors.InvalidEnumParameterException

/**
 * Enum class representing different roles or permission levels within the system.
 * Each level corresponds to a different set of privileges and access rights.
 */
enum class Level {

    /**
     * Represents the top-most privileged role in the system, with complete and unrestricted access to all areas and functionalities.
     */
    MASTER,

    /**
     * Represents a high-level access user, but does not have full control over the system.
     */
    MANAGER,

    /**
     * Represents a user with the ability to moderate content or manage user actions.
     */
    MODERATOR,

    /**
     * Represents a user assigned to driving tasks in the system, typically in logistics or transport-related systems.
     */
    OPERATIONAL;

    companion object {

        fun convertString(nStr: String?): Level {
            return nStr?.let { str ->
                if (enumExists(str)) Level.valueOf(str)
                else throw InvalidEnumParameterException("Received an invalid string for Level: $nStr.")
            } ?: throw NullPointerException("Received a null string and can not convert Level.")
        }

        fun enumExists(str: String): Boolean =
            entries.any { it.name == str }

    }

}