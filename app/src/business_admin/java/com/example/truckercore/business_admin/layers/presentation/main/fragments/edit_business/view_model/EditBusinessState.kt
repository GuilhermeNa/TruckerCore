package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.presentation.base.contracts.State

sealed class EditBusinessState: State {

    object Loading: EditBusinessState()

    object Failure: EditBusinessState()

    data class Loaded(val company: Company): EditBusinessState()

}