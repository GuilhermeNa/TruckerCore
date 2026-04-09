package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.layers.domain.model.company.Company

data class CompanyView(
    val cnpj: String = "",
    val cnpjError: Boolean = false,

    val name: String = "",
    val nameError: Boolean = false,

    val stateReg: String = "",
    val stateRegError: Boolean = false,

    val municipalReg: String = "",
    val municipalRegError: Boolean = false,

    val opening: String = "",
    val openingError: Boolean = false

) {

    companion object {
        fun from(company: Company): CompanyView {
            return CompanyView(
                cnpj = company.cnpj?.value ?: "",
                name = company.name?.value ?: "",
                stateReg = company.stateRegistration?.value ?: "",
                municipalReg = company.municipalRegistration?.value ?: "",
                opening = company.opening.toString()
            )
        }
    }

    fun updateCnpj(value: String): CompanyView {

        return copy(cnpj = TODO(), cnpjError = TODO())
    }

    fun updateName(value: String): CompanyView {

        return copy(name = TODO(), nameError = TODO())
    }

    fun updateStateReg(value: String): CompanyView {

        return copy(stateReg = TODO(), stateRegError = TODO())
    }

    fun updateMunicipalReg(value: String): CompanyView {

        return copy(municipalReg = TODO(), municipalRegError = TODO())
    }

    fun updateOpening(value: String): CompanyView {

        return copy(opening = TODO(), openingError = TODO())
    }

}