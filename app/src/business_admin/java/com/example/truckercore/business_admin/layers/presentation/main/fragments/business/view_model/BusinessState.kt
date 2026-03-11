package com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model

import com.example.truckercore.layers.presentation.base.contracts.State

data class BusinessState(
    private var _interactionEnabled: Boolean = true,
    val status: BusinessStatus = BusinessStatus.Loading
): State {

    val interactionEnabled get() = _interactionEnabled

    fun incomplete()= copy(status = BusinessStatus.Incomplete)

    fun complete()= copy(status = BusinessStatus.Complete)

    fun disableInteraction() = copy(_interactionEnabled = false)

    fun enableInteraction() = copy(_interactionEnabled = true)

}