package com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.truckercore.business_admin.layers.presentation.main.fragments.business.content.BusinessViewFactory
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.expressions.collectFoldRequired
import com.example.truckercore.layers.data_2.repository.interfaces.CompanyRepository
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.domain.singletons.session.SessionManager
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.StateManager
import kotlinx.coroutines.launch

private typealias LoadingState = BusinessState.Loading

class BusinessViewModel(
    private val sessionManager: SessionManager,
    private val repository: CompanyRepository
) : BaseViewModel() {

    private val stateManager = StateManager<BusinessState>(LoadingState)
    val stateFlow get() = stateManager.stateFlow

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    fun observeContent() = viewModelScope.launch {
        repository.observe(
            sessionManager.companyId()
        ).collectFoldRequired(::onSuccess, ::orElse)
    }

    private fun onSuccess(company: Company) {
        val content = BusinessViewFactory.from(company)
        val newState = stateManager.getState().showContent(content)
        stateManager.update(newState)
    }

    private fun orElse(exception: AppException) {
        Log.e(this::class.simpleName, LOAD_DATA_ERROR, exception)
        val newState = stateManager.getState().failure()
        stateManager.update(newState)
    }

    private companion object {
        private const val LOAD_DATA_ERROR = "Failed to fetch Company."
    }

}