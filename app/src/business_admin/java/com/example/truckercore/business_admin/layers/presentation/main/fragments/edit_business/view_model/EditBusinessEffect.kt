package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.layers.presentation.base.contracts.Effect

sealed class EditBusinessEffect : Effect {

    data class BindData(val data: EditBusinessView) : EditBusinessEffect()

}