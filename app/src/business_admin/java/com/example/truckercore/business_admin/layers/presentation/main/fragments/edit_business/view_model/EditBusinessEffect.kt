package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.layers.presentation.base.contracts.Effect

sealed class EditBusinessEffect : Effect {

    data class BindData(val data: EditBusinessView) : EditBusinessEffect()

    sealed class BindError : EditBusinessEffect() {
        data class Name(val text: String?) : BindError()
        data class Cnpj(val text: String?) : BindError()
        data class State(val text: String?) : BindError()
        data class Municipal(val text: String?) : BindError()
        data class Opening(val text: String?) : BindError()
    }

}