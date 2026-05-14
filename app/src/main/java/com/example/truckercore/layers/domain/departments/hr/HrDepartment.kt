package com.example.truckercore.layers.domain.departments.hr

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.contracts.Employee
import com.example.truckercore.layers.domain.base.contracts.EmployeeID
import com.example.truckercore.layers.domain.base.ids.AdminID
import com.example.truckercore.layers.domain.base.ids.DriverID
import com.example.truckercore.layers.domain.model.admin.Admin
import com.example.truckercore.layers.domain.model.driver.Driver

class HrDepartment {

    private val collection = EmployeeCollection()

    //----------------------------------------------------------------------------------------------
    // Initialize
    //----------------------------------------------------------------------------------------------
    fun initFromDatabase(employees: HashSet<Employee>) {
        collection.clear()
        employees.forEach(::register)
    }

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    fun register(employee: Employee) {
        if (collection.contains(employee.id)) {
            throw DomainException.RuleViolation(REGISTER_EMPLOYEE_ERROR_MSG)
        }

        collection.add(employee)
    }

    fun findEmployeeBy(id: EmployeeID): Employee? = collection.findBy(id)

    fun findDriverBy(id: DriverID): Driver? {
        val employee = collection.findBy(id) ?: return null

        if (employee !is Driver) {
            throw DomainException.RuleViolation(NOT_A_DRIVER_ERROR_MSG)
        }

        return employee
    }

    fun findAdminBy(id: AdminID): Admin? {
        val employee = collection.findBy(id) ?: return null

        if (employee !is Admin) {
            throw DomainException.RuleViolation(NOT_AN_ADMIN_ERROR_MSG)
        }

        return employee
    }

    fun listEmployees(): List<Employee> {
        return collection.data.toList()
    }

    companion object {

        private const val REGISTER_EMPLOYEE_ERROR_MSG =
            "Cannot register Employee: an employee with the same ID already exists."

        private const val NOT_A_DRIVER_ERROR_MSG =
            "Cannot retrieve Driver: the employee found for the given ID is not a driver."

        private const val NOT_AN_ADMIN_ERROR_MSG =
            "Cannot retrieve Admin: the employee found for the given ID is not an admin."

    }

}