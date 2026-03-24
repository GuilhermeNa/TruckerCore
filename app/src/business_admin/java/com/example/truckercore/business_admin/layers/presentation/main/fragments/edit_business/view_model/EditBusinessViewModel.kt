package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.expressions.foldRequired
import com.example.truckercore.layers.data_2.repository.interfaces.CompanyRepository
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.domain.singletons.session.SessionState
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.StateManager
import kotlinx.coroutines.launch

class EditBusinessViewModel(
    private val sessionState: SessionState,
    private val repository: CompanyRepository
) : BaseViewModel() {

    private val stateManager = StateManager<EditBusinessState>(EditBusinessState.Loading)
    val stateFlow = stateManager.stateFlow

    private val id
        get() = sessionState.companyId
            ?: throw IllegalStateException(ERROR)

    //----------------------------------------------------------------------------------------------
    fun fetchCompany() {
        viewModelScope.launch {
            repository.fetch(id)
                .foldRequired(::handleSuccess, ::handleError)
        }
    }

    private fun handleSuccess(company: Company) {
        val newState = EditBusinessState.Loaded(company)
        stateManager.update(newState)
    }

    private fun handleError(error: AppException) {
        Log.e(classTag, "${error.message}", error)
        val newState = EditBusinessState.Failure
        stateManager.update(newState)
    }


    private companion object {
        private const val ERROR = "Company id could not be loaded in EditBusinessViewModel"
    }

}