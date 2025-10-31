package com.example.truckercore.layers.domain.departments.hr

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.contracts.entity.EmployeeID
import com.example.truckercore.layers.domain.base.contracts.others.Employee
import com.example.truckercore.layers.domain.base.ids.AdminID
import com.example.truckercore.layers.domain.base.ids.DriverID
import com.example.truckercore.layers.domain.model.admin.Admin
import com.example.truckercore.layers.domain.model.driver.Driver

class HrDepartment {

    private val employees = EmployeeCollection()

    //----------------------------------------------------------------------------------------------
    // Initialize
    //----------------------------------------------------------------------------------------------
    fun initFromDatabase(employeeList: List<Employee>) {
        employeeList.forEach(::registerEmployee)
    }

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    fun registerEmployee(employee: Employee) {
        if (employees.contains(employee.id)) {
            throw DomainException.RuleViolation(REGISTER_EMPLOYEE_ERROR_MSG)
        } else employees.add(employee)
    }

    fun findEmployeeBy(id: EmployeeID): Employee? =
        employees.findBy(id)

    fun findDriverBy(id: DriverID): Driver? = employees.findBy(id)?.let { employee ->
        if (employee !is Driver) {
            throw DomainException.RuleViolation(NOT_A_DRIVER_ERROR_MSG)
        } else return employee
    }

    fun findAdminBy(id: AdminID): Admin? = employees.findBy(id)?.let { employee ->
        if (employee !is Admin) {
            throw DomainException.RuleViolation(NOT_AN_ADMIN_ERROR_MSG)
        } else return employee
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