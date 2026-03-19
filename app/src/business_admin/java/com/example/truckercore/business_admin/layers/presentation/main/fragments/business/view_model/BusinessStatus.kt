package com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model

sealed class BusinessStatus {

    data object Loading: BusinessStatus()

    data object Incomplete: BusinessStatus()

    data object Complete: BusinessStatus()

    data object Failure: BusinessStatus()

}