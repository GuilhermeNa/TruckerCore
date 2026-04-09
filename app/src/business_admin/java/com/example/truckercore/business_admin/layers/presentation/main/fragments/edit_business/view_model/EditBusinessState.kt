package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.layers.presentation.base.contracts.State

data class EditBusinessState(
    val companyView: CompanyView = CompanyView(),
    val status: EditBusinessStatus = EditBusinessStatus.Loading
) : State {

    private val validator: EditBusinessValidator = EditBusinessValidator()

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    fun companyFound(companyView: CompanyView) = copy(
        companyView = companyView,
        status = EditBusinessStatus.Loaded
    )

    fun failure() = copy(status = EditBusinessStatus.Failure)

    fun updateName(value: String) = copy(companyView = companyView.updateName(value))

    fun updateCnpj(value: String) = copy(companyView = companyView.updateCnpj(value))

    fun updateState(value: String) = copy(companyView = companyView.updateStateReg(value))

    fun updateMunicipal(value: String) = copy(companyView = companyView.updateMunicipalReg(value))

    fun updateOpening(value: String) = copy(companyView = companyView.updateOpening(value))

}