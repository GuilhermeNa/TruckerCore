package com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.content

import com.example.truckercore.layers.domain.base.contracts.Employee

object EmployeesViewFactory {

    fun from(employees: List<Employee>) =
        EmployeesView(
            getItems(employees)
        )

    private fun getItems(employees: List<Employee>) =
        employees.map { emp ->
            EmployeesViewItem(
                id = emp.id,
                name = emp.name.value,
                position = emp.position()
            )
        }

}