package com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.view_model

import androidx.lifecycle.viewModelScope
import com.example.truckercore.business_admin.layers.domain.use_case.employee.GetEmployeesUseCase
import com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model.BusinessState
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.StateManager
import kotlinx.coroutines.launch

private typealias LoadingState = EmployeesState.Loading

class EmployeesViewModel(
    private val getEmployeesUseCase: GetEmployeesUseCase
): BaseViewModel() {

    private val stateManager = StateManager<EmployeesState>(LoadingState)
    val stateFlow get() = stateManager.stateFlow

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    fun observeContent() = viewModelScope.launch {
        TODO("Not yet implemented")
    }

    private fun onSuccess() {

    }

    private fun orElse() {

    }




}