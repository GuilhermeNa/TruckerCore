package com.example.truckercore.modules.employee.shared.modules.employee_contract.enums

import com.example.truckercore.shared.exceptions.InvalidStateException
import java.security.InvalidParameterException

enum class EmployeeContractStatus {
    EXPERIENCE, // The contract is in the employee's probationary period.
    PERMANENT, // The contract is permanent, with no planned termination date.
    TEMPORARY, // The contract is temporary, with a specific end date.
    TERMINATED; // The contract has been terminated, and the employee is no longer active.

    companion object {
        /**
         * Validates if the current contract status is within a list of valid statuses.
         *
         * @param actualStatus The current contract status to validate.
         * @param validStatuses The list of valid contract statuses.
         * @throws InvalidStateException if the current contract status is not one of the valid states.
         */
        fun validateStatus(actualStatus: EmployeeContractStatus, vararg validStatuses: EmployeeContractStatus) {
            if (validStatuses.isEmpty()) throw InvalidParameterException(
                "The validStatuses must have at least one state to be validated."
            )
            if (actualStatus !in validStatuses) throw InvalidStateException(
                "Expected the contract status to be one of the following: " +
                        "${validStatuses.joinToString(", ", "[", "]")}." +
                        " The actual contract status is: $actualStatus"
            )
        }
    }
}