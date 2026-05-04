package com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model

import com.example.truckercore.business_admin.layers.presentation.main.fragments.business.content.BusinessView
import com.example.truckercore.layers.presentation.base.contracts.State

sealed class BusinessState: State {
    
    data object Loading: BusinessState()

    data class Content(val data: BusinessView): BusinessState()

    data object Failure: BusinessState()

    //----------------------------------------------------------------------------------------------

    fun loading() = Loading

    fun showContent(data: BusinessView) = Content(data)

    fun failure() = Failure

}