package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

class EditBusinessValidator {

    private var nameValid: Boolean = false
    private var cnpjValid: Boolean = false
    private var stateValid: Boolean = false
    private var municipalValid: Boolean = false
    private var openingValid: Boolean = false

    val readyToSave get() = nameValid && cnpjValid && stateValid && municipalValid && openingValid

    fun updateName(value: String) {

    }

    fun updateCnpj(value: String) {

    }

    fun updateState(value: String) {

    }

    fun updateMunicipal(value: String) {

    }

    fun updateOpening(value: String) {

    }

}