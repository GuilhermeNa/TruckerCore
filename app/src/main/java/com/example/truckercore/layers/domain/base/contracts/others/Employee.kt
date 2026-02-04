package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.layers.domain.base.contracts.entity.EmployeeID

/**
 * Base contract for all employees.
 *
 * Employees are persons associated with a company and
 * identified by an EmployeeID.
 */
interface Employee : Person {
    override val id: EmployeeID
}