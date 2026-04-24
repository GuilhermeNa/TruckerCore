package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.data.EditBusinessValidator
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.data.EditBusinessView
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.expressions.foldRequired
import com.example.truckercore.layers.data_2.repository.interfaces.CompanyRepository
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.domain.singletons.session.SessionManager
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.StateManager
import kotlinx.coroutines.launch

private typealias Validator = EditBusinessValidator
private typealias LoadingState = EditBusinessState.Loading

class EditBusinessViewModel(
    private val sessionManager: SessionManager,
    private val repository: CompanyRepository
) : BaseViewModel() {

    private val stateManager = StateManager<EditBusinessState>(LoadingState)
    val stateFlow = stateManager.stateFlow

    private val validator = Validator()

    private var _isViewInitialized: Boolean = false
    val isViewInit get() = _isViewInitialized

    //----------------------------------------------------------------------------------------------
    // FETCH COMPANY
    //----------------------------------------------------------------------------------------------
    fun fetchCompany() {
        viewModelScope.launch {
            repository.fetch(id = sessionManager.companyId())
                .foldRequired(::fetchSuccess, ::fetchError)
        }
    }

    private fun fetchSuccess(company: Company) {
        val data = EditBusinessView.from(company)
        val newState = stateManager.currentState().loaded(data)
        stateManager.update(newState)
    }

    private fun fetchError(error: AppException) {
        Log.e(classTag, "${error.message}", error)
        val newState = stateManager.currentState().failure()
        stateManager.update(newState)
    }

    //----------------------------------------------------------------------------------------------
    // SAVE COMPANY
    //----------------------------------------------------------------------------------------------
    fun saveCompany(data: EditBusinessView) {

        // Update State
        val newState = stateManager.currentState().saving()
        stateManager.update(newState)

        viewModelScope.launch {
            repository


        }
    }

    private fun saveSuccess() {

        //


        // Update State
        val newState = stateManager.currentState().saved()
        stateManager.update(newState)

    }

    private fun saveError() {

    }


    //----------------------------------------------------------------------------------------------
    // PUBLIC
    //----------------------------------------------------------------------------------------------
    fun markAsInitialized() {
        _isViewInitialized = true
    }

    fun updateName(text: String) {
        val newState = stateManager.currentState().textChange(
            validationMap = validator.validateName(text),
            fieldsAreValid = validator.fieldsAreValid
        )
        stateManager.update(newState)
    }

    fun updateCnpj(text: String) {
        val newState = stateManager.currentState().textChange(
            validationMap = validator.validateCnpj(text),
            fieldsAreValid = validator.fieldsAreValid
        )
        stateManager.update(newState)
    }

    fun updateState(text: String) {
        val newState = stateManager.currentState().textChange(
            validationMap = validator.validateState(text),
            fieldsAreValid = validator.fieldsAreValid
        )
        stateManager.update(newState)
    }

    fun updateMunicipal(text: String) {
        val newState = stateManager.currentState().textChange(
            validationMap = validator.validateMunicipal(text),
            fieldsAreValid = validator.fieldsAreValid
        )
        stateManager.update(newState)
    }

    fun updateOpening(text: String) {
        val newState = stateManager.currentState().textChange(
            validationMap = validator.validateOpening(text),
            fieldsAreValid = validator.fieldsAreValid
        )
        stateManager.update(newState)
    }



}
