package com.example.truckercore.modules.user.enums

import com.example.truckercore.shared.errors.InvalidStateException
import java.security.InvalidParameterException

enum class PermissionLevel {
    /**
     * Represents the operational level permission.
     * Typically assigned to users who have basic operational privileges in the system like drivers.
     */
    OPERATIONAL,

    /**
     * Represents the assistant level permission.
     * This level is granted to users with more privileges than the operational level but less than the manager.
     */
    ASSISTANT,

    /**
     * Represents the manager level permission.
     * This level provides users with the ability to manage operations and supervise lower-level roles.
     */
    MANAGER,

    /**
     * Represents the highest level of permission in the system.
     * Users with this level typically have full access to all functionalities and data.
     */
    MASTER;

    companion object {
        /**
         * Validates if the current permission level is within a list of valid permission levels.
         *
         * @param actualState The current permission level to validate.
         * @param validStatuses The list of valid permission levels.
         * @throws InvalidStateException if the current permission level is not one of the valid states.
         */
        fun validateState(actualState: PermissionLevel, vararg validStatuses: PermissionLevel) {
            if (validStatuses.isEmpty()) throw InvalidParameterException(
                "The validStatuses must have at least one state to be validated."
            )
            if (actualState !in validStatuses) throw InvalidStateException(
                "Expected the permission level to be one of the following: " +
                        "${validStatuses.joinToString(", ", "[", "]")}." +
                        " The actual permission level is: $actualState"
            )
        }
    }

}