package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.presentation.base.contracts.State

data class EditBusinessState(
    val companyView: CompanyView = CompanyView(),
    val status: EditBusinessStatus = EditBusinessStatus.Loading
) : State {

    private val validator: EditBusinessValidator = EditBusinessValidator()

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    fun companyFound(company: Company) = copy(
        companyView = CompanyView.from(company),
        status = EditBusinessStatus.Loaded
    )

    fun failure() = copy(status = EditBusinessStatus.Failure)

    fun updateName(value: String): EditBusinessState {
        val newState = validator.
    }

    fun updateCnpj(value: String): EditBusinessState {
        val newState = state.updateCnpj(value)
    }

    fun updateState(value: String): EditBusinessState {
        val newState = state.updateState(value)
    }

    fun updateMunicipal(value: String): EditBusinessState {
        val newState = state.updateMunicipal(value)
    }

    fun updateOpening(value: String): EditBusinessState {
        val newState = state.updateOpening(value)
    }


}