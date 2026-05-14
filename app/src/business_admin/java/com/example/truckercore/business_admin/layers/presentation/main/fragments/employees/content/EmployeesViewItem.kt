package com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.content

import com.example.truckercore.layers.domain.base.contracts.EmployeeID

data class EmployeesViewItem(
    val id: EmployeeID,
    val name: String,
    val position: String,
)
