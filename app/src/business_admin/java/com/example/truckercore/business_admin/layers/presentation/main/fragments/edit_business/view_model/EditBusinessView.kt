package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.layers.domain.model.company.Company

data class EditBusinessView(
    val cnpj: String = "",
    val name: String = "",
    val stateReg: String = "",
    val municipalReg: String = "",
    val opening: String = "",
) {

    companion object {
        fun from(company: Company): EditBusinessView {
            return EditBusinessView(
                cnpj = company.cnpj?.value ?: "",
                name = company.name?.value ?: "",
                stateReg = company.stateRegistration?.value ?: "",
                municipalReg = company.municipalRegistration?.value ?: "",
                opening = company.opening?.toString() ?: ""
            )
        }
    }

}