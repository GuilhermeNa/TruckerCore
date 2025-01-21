package com.example.truckercore.infrastructure.security.permissions.enums

import com.example.truckercore.shared.errors.InvalidEnumParameterException

/**
 * Enum class representing various permissions that can be assigned to users or services.
 *
 * This enum defines the set of actions that a user or service can perform within the system.
 * Each permission corresponds to a specific action, such as creating, updating, deleting,
 * or viewing entities related to business central, users, or personal data.
 *
 * Permissions are typically checked before performing sensitive or restricted actions
 * to ensure that the user or service has the necessary access rights.
 */
enum class Permission {
    /**
     * Permission to create a new driver.
     */
    CREATE_DRIVER,

    /**
     * Permission to update a driver.
     */
    UPDATE_DRIVER,

    /**
     * Permission to delete a driver.
     */
    DELETE_DRIVER,

    /**
     * Permission to view a driver.
     */
    VIEW_DRIVER,

    /**
     * Permission to update a business central resource.
     */
    UPDATE_BUSINESS_CENTRAL,

    /**
     * Permission to delete a business central resource.
     */
    DELETE_BUSINESS_CENTRAL,

    /**
     * Permission to view a business central resource.
     */
    VIEW_BUSINESS_CENTRAL,

    /**
     * Permission to create a new user.
     */
    CREATE_USER,

    /**
     * Permission to update an existing user.
     */
    UPDATE_USER,

    /**
     * Permission to archive a user (typically, marking the user as inactive or removed).
     */
    ARCHIVE_USER,

    /**
     * Permission to delete a user from the system.
     */
    DELETE_USER,

    /**
     * Permission to view user details.
     */
    VIEW_USER,

    /**
     * Permission to create personal data records.
     */
    CREATE_PERSONAL_DATA,

    /**
     * Permission to update personal data records.
     */
    UPDATE_PERSONAL_DATA,

    /**
     * Permission to archive personal data (e.g., mark as inactive).
     */
    ARCHIVE_PERSONAL_DATA,

    /**
     * Permission to delete personal data records.
     */
    DELETE_PERSONAL_DATA,

    /**
     * Permission to view personal data records.
     */
    VIEW_PERSONAL_DATA;

    companion object {

        fun convertString(nStr: String?): Permission {
            return nStr?.let { str ->
                if (enumExists(str)) Permission.valueOf(str)
                else throw InvalidEnumParameterException("Received an invalid string for Permission: $nStr.")
            }
                ?: throw NullPointerException("Received a null string and can not convert Permission.")
        }

        fun enumExists(str: String): Boolean =
            entries.any { it.name == str }

    }
}