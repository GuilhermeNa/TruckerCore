package com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.expressions.foldRequired
import com.example.truckercore.layers.data_2.repository.interfaces.CompanyRepository
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.domain.singletons.session.SessionManager
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.StateManager
import kotlinx.coroutines.launch

class BusinessViewModel(
    private val sessionManager: SessionManager,
    private val repository: CompanyRepository
) : BaseViewModel() {

    private val stateManager = StateManager(BusinessState())
    val stateFlow get() = stateManager.stateFlow
    val currentState get() = stateManager.currentState()

    val name get() = valueOrDefault(currentState.name)
    val cnpj get() = valueOrDefault(currentState.cnpj)
    val opening get() = valueOrDefault(currentState.opening)
    val municipalInsc get() = valueOrDefault(currentState.municipalInsc)
    val stateInsc get() = valueOrDefault(currentState.stateInsc)

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    fun initialize() {
        viewModelScope.launch {
            repository.fetch(
                id = sessionManager.companyId()
            ).foldRequired(
                onSuccess = ::handleSuccess,
                orElse = ::handleFailure
            )
        }
    }

    private fun handleSuccess(company: Company) {
        val newState = when (company.isFilled()) {
            true -> currentState.complete(company)
            false -> currentState.incomplete(company)
        }

        stateManager.update(newState)
    }

    private fun handleFailure(exception: AppException) {
        Log.e(this::class.simpleName, LOAD_DATA_ERROR, exception)
        val newState = currentState.failure()
        stateManager.update(newState)
    }

    private fun valueOrDefault(s: String?) = s ?: DEFAULT

    private companion object {
        private const val DEFAULT = "-"
        private const val LOAD_DATA_ERROR = "Failed to fetch Company."
    }

}