package com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.content

data class EmployeesView(
    private val _data: List<EmployeesViewItem>
) {

    val byNameAsc: List<EmployeesViewItem> = _data.sortedBy { it.name }

}
