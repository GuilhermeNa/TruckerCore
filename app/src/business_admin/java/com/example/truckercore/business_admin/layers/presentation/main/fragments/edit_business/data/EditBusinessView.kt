package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.data

import com.example.truckercore.layers.domain.model.company.Company

private typealias Validator = EditBusinessValidator

data class EditBusinessView(
    val cnpj: String = "",
    val name: String = "",
    val stateReg: String = "",
    val municipalReg: String = "",
    val opening: String = "",

    val nameError: String? = null,
    val cnpjError: String? = null,
    val stateRegError: String? = null,
    val municipalRegError: String? = null,
    val openingError: String? = null
) {

    fun update(map: Map<Int, String?>) =
        copy(
            nameError = map[Validator.NAME],
            cnpjError = map[Validator.CNPJ],
            stateRegError = map[Validator.STATE],
            municipalRegError = map[Validator.MUNICIPAL],
            openingError = map[Validator.OPENING]
        )

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