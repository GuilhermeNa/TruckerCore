package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.data

import java.security.InvalidParameterException

class EditBusinessValidator {

    private val validationHash: HashMap<Int, String?> = hashMapOf(
        NAME to null,
        CNPJ to null,
        STATE to null,
        MUNICIPAL to null,
        OPENING to null
    )

    val fieldsAreValid get() = validationHash.values.all { it == null }

    operator fun invoke(field: Int, errorTxt: String?) {
        if (field !in 1..5) {
            throw InvalidParameterException(INVALID_PARAM)
        }
        validationHash[field] = errorTxt
    }

    //---------------------

    fun validateName(text: String): Map<Int, String?> {
        val clean = text.trim()
        val hasMinimumSize = clean.length >= NAME_MIN_SIZE

        if (clean.isEmpty() || hasMinimumSize) {
            validationHash[NAME] = null
        } else {
            validationHash[NAME] = SHORT_NAME
        }

        return validationHash.toMap()
    }

    fun validateCnpj(text: String): Map<Int, String?> {
        val clean = text
            .replace(Regex("\\D"), "")
            .trim()
        val correctSize = clean.length == CNPJ_SIZE

        if (clean.isEmpty() || correctSize) {
            validationHash[CNPJ] = null
        } else {
            validationHash[CNPJ] = INCORRECT_CNPJ
        }

        return validationHash.toMap()
    }

    fun validateState(text: String): Map<Int, String?> {
        val clean = text.trim()
        val correctSize = clean.length in STATE_MIN_SIZE..STATE_MAX_SIZE

        if (text.isEmpty() || correctSize) {
            validationHash[STATE] = null
        } else {
            validationHash[STATE] = INCORRECT_STATE
        }

        return validationHash.toMap()
    }

    fun validateMunicipal(text: String): Map<Int, String?> {
        val clean = text.trim()
        val correctSize = clean.length in MUNICIPAL_MIN_SIZE..MUNICIPAL_MAX_SIZE

        if (text.isEmpty() || correctSize) {
            validationHash[MUNICIPAL] = null
        } else {
            validationHash[MUNICIPAL] = INCORRECT_MUNICIPAL
        }

        return validationHash.toMap()
    }

    fun validateOpening(text: String): Map<Int, String?> {
        val clean = text
            .replace(Regex("\\D"), "")
            .trim()
        val correctSize = clean.length == DATE_SIZE

        if (clean.isEmpty() || correctSize) {
            validationHash[OPENING] = null
        } else {
            validationHash[OPENING] = INCORRECT_DATE
        }

        return validationHash.toMap()
    }

    companion object {
        private const val INVALID_PARAM = "Field value should be in 1 to 5 range"

        const val NAME = 1
        const val CNPJ = 2
        const val STATE = 3
        const val MUNICIPAL = 4
        const val OPENING = 5

        private const val CNPJ_SIZE = 14
        private const val NAME_MIN_SIZE = 3
        private const val DATE_SIZE = 6

        private const val STATE_MIN_SIZE = 8
        private const val STATE_MAX_SIZE = 12

        private const val MUNICIPAL_MIN_SIZE = 7
        private const val MUNICIPAL_MAX_SIZE = 15

        private const val INCORRECT_DATE = "Data deve ter 6 caracteres"
        private const val SHORT_NAME = "Nome deve ter pelo menos 3 caracteres"
        private const val INCORRECT_CNPJ = "Cnpj deve ter 14 caracteres"
        private const val INCORRECT_STATE = "Deve ter entre 8 e 12 caracteres"
        private const val INCORRECT_MUNICIPAL = "Deve ter entre 7 e 15 caracteres"


    }

}