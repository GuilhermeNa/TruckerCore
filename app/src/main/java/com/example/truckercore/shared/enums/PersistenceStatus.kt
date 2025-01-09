package com.example.truckercore.shared.enums

import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.errors.InvalidEnumParameterException
import java.security.InvalidParameterException

enum class PersistenceStatus {
    PENDING, // Indicates that the object has not yet been persisted (saved) to the database
    PERSISTED, // Represents that the object has been successfully persisted (saved) to the database
    ARCHIVED; // Denotes that the object has been "logically deleted" from the system but still in database.

    companion object {

        /**
         * Validates if the current status is among the valid states passed as arguments.
         *
         * @param actualStatus The current status to validate.
         * @param validStatuses The list of valid statuses to check against.
         * @throws InvalidStateException if the current status is not one of the valid statuses.
         */
        fun validateState(
            actualStatus: PersistenceStatus,
            vararg validStatuses: PersistenceStatus
        ) {
            if (validStatuses.isEmpty()) throw InvalidParameterException(
                "The validStatuses must have at least one state to be validated."
            )
            if (actualStatus !in validStatuses) throw InvalidStateException(
                "Expected the status to be one of the following: " +
                        "${validStatuses.joinToString(", ", "[", "]")}." +
                        " The actual status is: $actualStatus"
            )
        }

        fun convertString(nStr: String?): PersistenceStatus {
            return nStr?.let { str ->
                if (entries.any { it.name == str }) valueOf(str)
                else throw InvalidEnumParameterException()
            } ?: throw IllegalArgumentException()
        }

        fun enumExists(str: String): Boolean =
            entries.any { it.name == str }

    }


}