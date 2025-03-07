package com.example.truckercore.model.modules.person.employee.shared.enums

import com.example.truckercore.model.shared.errors.InvalidEnumParameterException

/**
 * Enum class representing the different possible statuses of an employee within the system.
 * This enum is used to track the current state of an employee's availability and work status.
 *
 * The statuses can help determine if an employee is available to perform their duties or if they are temporarily unavailable due to vacation or leave.
 */
enum class EmployeeStatus {

    WORKING,
    VACATION,
    ON_LEAVE;

    companion object {

        fun convertString(nStr: String?): EmployeeStatus {
            return nStr?.let { str ->
                if (enumExists(str)) valueOf(str)
                else throw InvalidEnumParameterException("Received an invalid string for EmployeeStatus: $nStr.")
            }
                ?: throw NullPointerException("Received a null string and can not convert EmployeeStatus.")
        }

        fun enumExists(str: String): Boolean =
            entries.any { it.name == str }

    }

}