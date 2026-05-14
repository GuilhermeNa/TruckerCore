package com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.truckercore.business_admin.layers.domain.use_case.admin.GetHrDepartmentUseCase
import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.content.EmployeesViewFactory
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.expressions.collectFoldRequired
import com.example.truckercore.layers.domain.departments.hr.HrDepartment
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.StateManager
import kotlinx.coroutines.launch

class EmployeesViewModel(
    private val useCase: GetHrDepartmentUseCase
) : BaseViewModel() {

    private val stateManager = StateManager<EmployeesState>(EmployeesState.Loading)
    val stateFlow get() = stateManager.stateFlow

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    fun observeContent() = viewModelScope.launch {
        useCase.observe().collectFoldRequired(::onSuccess, ::orElse)
    }

    private fun onSuccess(hr: HrDepartment) {
        val content = EmployeesViewFactory.from(
            hr.listEmployees()
        )
        val newState = stateManager.getState().showContent(content)
        stateManager.update(newState)
    }

    private fun orElse(exception: AppException) {
        Log.e(this::class.simpleName, LOAD_DATA_ERROR, exception)
        val newState = stateManager.getState().failure()
        stateManager.update(newState)
    }

    private companion object {
        private const val LOAD_DATA_ERROR = "Failed to fetch Employees."
    }

}