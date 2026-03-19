package com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model

import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.presentation.base.contracts.State

data class BusinessState(
    val company: Company? = null,
    private var _interactionEnabled: Boolean = true,
    val status: BusinessStatus = BusinessStatus.Loading
) : State {

    val interactionEnabled get() = _interactionEnabled

    val name get() = company?.name?.value

    val cnpj get() = company?.cnpj?.value

    val opening get() = company?.opening?.toString()

    val municipalInsc get() = company?.municipalRegistration?.value

    val stateInsc get() = company?.stateRegistration?.value

    //----------------------------------------------------------------------------------------------

    fun incomplete(company: Company) = copy(status = BusinessStatus.Incomplete)

    fun complete(company: Company) = copy(status = BusinessStatus.Complete)

    fun failure() = copy(status = BusinessStatus.Failure)

    fun disableInteraction() = copy(_interactionEnabled = false)

    fun enableInteraction() = copy(_interactionEnabled = true)

}