package com.example.truckercore.modules.employee.shared.enums

import com.example.truckercore.shared.errors.InvalidEnumParameterException
import com.example.truckercore.shared.errors.InvalidStateException
import java.security.InvalidParameterException

enum class EmployeeStatus {

    /**
     * The employee is actively working.
     */
    WORKING,

    /**
     * The employee is on vacation.
     */
    VACATION,

    /**
     * The employee is on a leave of absence (could be personal, sick leave, etc.).
     */
    ON_LEAVE;

    companion object {

        /**
         * Validates if the current employee status is within a list of valid statuses.
         *
         * @param actualStatus The current employee status to validate.
         * @param validStatuses The list of valid employee statuses.
         * @throws InvalidStateException if the current employee status is not one of the valid states.
         */
        fun validateStatus(actualStatus: EmployeeStatus, vararg validStatuses: EmployeeStatus) {
            if (validStatuses.isEmpty()) throw InvalidParameterException(
                "The validStatuses must have at least one state to be validated."
            )
            if (actualStatus !in validStatuses) throw InvalidStateException(
                "Expected the employee status to be one of the following: " +
                        "${validStatuses.joinToString(", ", "[", "]")}." +
                        " The actual employee status is: $actualStatus"
            )
        }

        fun convertString(nStr: String?): EmployeeStatus {
            return nStr?.let { str ->
                if (enumExists(str)) valueOf(str)
                else throw InvalidEnumParameterException("Received an invalid string for EmployeeStatus: $nStr.")
            } ?: throw NullPointerException("Received a null string and can not convert EmployeeStatus.")
        }

        private fun enumExists(str: String): Boolean =
            entries.any { it.name == str }

    }

}