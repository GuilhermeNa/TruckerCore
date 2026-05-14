package com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.view_model

import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.content.EmployeesView
import com.example.truckercore.layers.presentation.base.contracts.State

sealed class EmployeesState : State {
    
    data object Loading : EmployeesState()

    data class Content(val data: EmployeesView) : EmployeesState()

    data object Failure : EmployeesState()

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    fun loading() = Loading

    fun showContent(data: EmployeesView) = Content(data)

    fun failure() = Failure

}