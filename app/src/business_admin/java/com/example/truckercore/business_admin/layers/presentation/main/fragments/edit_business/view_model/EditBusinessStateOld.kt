package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.layers.presentation.base.contracts.State

data class EditBusinessStateOld(
    val companyView: EditBusinessView = EditBusinessView(),
    val status: EditBusinessStatus = EditBusinessStatus.Loading
) : State {

    val hasError get() = !companyView.hasError

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    fun companyFound(editBusinessView: EditBusinessView) = copy(
        companyView = editBusinessView,
        status = EditBusinessStatus.Loaded
    )

    fun failure() = copy(status = EditBusinessStatus.Failure)

    fun updateName(value: String) = copy(companyView = companyView.updateName(value))

    fun updateCnpj(value: String) = copy(companyView = companyView.updateCnpj(value))

    fun updateState(value: String) = copy(companyView = companyView.updateStateReg(value))

    fun updateMunicipal(value: String) = copy(companyView = companyView.updateMunicipalReg(value))

    fun updateOpening(value: String) = copy(companyView = companyView.updateOpening(value))

}