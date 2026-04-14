package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.layers.domain.model.company.Company

data class EditBusinessView(
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

    val hasError
        get() = cnpjError || nameError || stateRegError ||
                municipalRegError || openingError

    val day get() = opening.take(2).toInt()
    val month get() = opening.drop(2).take(2).toInt()
    val year get() = opening.takeLast(2).toInt()

    private fun onlyDigits(value: String): String {
        return value.replace(Regex("[^0-9]"), "")
    }

    fun updateName(value: String): EditBusinessView {
        if (value.isBlank()) return copy(name = "", nameError = false)

        val clean = value.trim()
        val isValid = clean.length >= 3

        return copy(
            name = clean,
            nameError = !isValid
        )
    }

    fun updateCnpj(value: String): EditBusinessView {
        if (value.isBlank()) return copy(cnpj = "", cnpjError = false)

        val clean = onlyDigits(value)
        val isValid = clean.length == 14

        return copy(
            cnpj = clean,
            cnpjError = !isValid
        )
    }

    fun updateStateReg(value: String): EditBusinessView {
        if (value.isBlank()) return copy(stateReg = "", stateRegError = false)

        val clean = value.trim()
        val isValid = clean.length in 8..12

        return copy(
            stateReg = clean,
            stateRegError = !isValid
        )
    }

    fun updateMunicipalReg(value: String): EditBusinessView {
        if (value.isBlank()) return copy(municipalReg = "", municipalRegError = false)

        val clean = value.trim()
        val isValid = clean.length in 7..15

        return copy(
            municipalReg = clean,
            municipalRegError = !isValid
        )
    }

    fun updateOpening(value: String): EditBusinessView {
        if (value.isBlank()) return copy(opening = "", openingError = false)

        // Exemplo: valida formato de data simples DD-MM-YY
        val clean = value.trim()
        val regex = Regex("^\\d{2}-\\d{2}-\\d{2}$")
        val isValid = regex.matches(clean)

        return copy(
            opening = clean,
            openingError = !isValid
        )
    }

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