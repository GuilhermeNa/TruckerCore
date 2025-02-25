package com.example.truckercore.shared.enums

import com.example.truckercore.shared.errors.InvalidEnumParameterException

/**
 * Enum class representing the persistence status of an entity in the system.
 * This status indicates the state of the object with respect to its persistence in the database.
 *
 * The `PersistenceStatus` enum is used to track and manage the lifecycle of an entity in terms of its persistence state.
 *
 */
enum class PersistenceStatus {
    PENDING, // Indicates that the object has not yet been persisted (saved) to the database
    PERSISTED, // Represents that the object has been successfully persisted (saved) to the database
    ARCHIVED; // Denotes that the object has been "logically deleted" from the system but still in database.

    companion object {

        fun convertString(nStr: String?): PersistenceStatus {
            return nStr?.let { str ->
                if (enumExists(str)) valueOf(str)
                else throw InvalidEnumParameterException("Received an invalid string for PersistenceStatus: $nStr.")
            }
                ?: throw NullPointerException("Received a null string and can not convert PersistenceStatus.")
        }

        fun enumExists(str: String): Boolean =
            entries.any { it.name == str }

    }

}