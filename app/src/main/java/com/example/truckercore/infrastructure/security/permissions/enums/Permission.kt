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
    VIEW_LICENSING,
    CREATE_LICENSING,
    UPDATE_LICENSING,
    DELETE_LICENSING,

    VIEW_TRAILER,
    CREATE_TRAILER,
    DELETE_TRAILER,
    UPDATE_TRAILER,

    VIEW_TRUCK,
    CREATE_TRUCK,
    DELETE_TRUCK,
    UPDATE_TRUCK,

    UPDATE_FILE,
    CREATE_FILE,
    VIEW_FILE,
    DELETE_FILE,

    CREATE_ADMIN,
    UPDATE_ADMIN,
    DELETE_ADMIN,
    VIEW_ADMIN,

    CREATE_DRIVER,
    UPDATE_DRIVER,
    DELETE_DRIVER,
    VIEW_DRIVER,

    CREATE_BUSINESS_CENTRAL,
    UPDATE_BUSINESS_CENTRAL,
    DELETE_BUSINESS_CENTRAL,
    VIEW_BUSINESS_CENTRAL,

    CREATE_USER,
    UPDATE_USER,
    ARCHIVE_USER,
    DELETE_USER,
    VIEW_USER,

    CREATE_PERSONAL_DATA,
    UPDATE_PERSONAL_DATA,
    ARCHIVE_PERSONAL_DATA,
    DELETE_PERSONAL_DATA,
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

