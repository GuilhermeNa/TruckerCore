package com.example.truckercore.layers.domain.base.contracts

import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.view_model.EmployeesState

/**
 * Base contract for all employees.
 *
 * Employees are persons associated with a company and
 * identified by an EmployeeID.
 */
interface Employee : Person {
    override val id: EmployeeID
    val state: EmployeesState
    fun position(): String

}